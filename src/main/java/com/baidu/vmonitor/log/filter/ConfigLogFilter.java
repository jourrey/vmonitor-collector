package com.baidu.vmonitor.log.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.annotation.DoLogClass;
import com.baidu.vmonitor.collector.CollectorInterface;
import com.baidu.vmonitor.common.VmonitorContext;
import com.baidu.vmonitor.log.filter.collector.FilterCollectorDecorator;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;

public class ConfigLogFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(ConfigLogFilter.class);
    
    private static final String COLLECTOR_CLASS = "collectorClass";
    
    private static final String KEY = "key";
    
    private static final String BEHAVIOR_TYPE = "behaviorType";
    
    private static final String VALUE = "value";

    // FilterConfig可用于访问Filter的配置信息
    private FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                    ServletException {
        LOG.debug("enter doFilter");
        if (StringUtils.isBlank(filterConfig.getInitParameter(VALUE)) || Boolean.parseBoolean(filterConfig.getInitParameter(VALUE))) {//启用日志监控
            /* 初始化日志配置 */
            CollectorParam collectorParam = null;
            try {
                collectorParam = getCollectorParam();
                collectorParam.setParameters(new Object[] { request, response, chain });
            } catch (Exception e) {
                LOG.error("new CollectorParam failure", e);
            }
            /* 日志记录开始 */
            FilterCallLogInfo logInfo = FilterCollectorDecorator.getInstance().doCall(collectorParam);
            try {
                chain.doFilter(request, response);
                FilterCollectorDecorator.getInstance().doAnswer(collectorParam, logInfo, null);
            } catch (IOException e) {
                FilterCollectorDecorator.getInstance().doException(collectorParam, logInfo, e);
            } catch (ServletException e) {
                FilterCollectorDecorator.getInstance().doException(collectorParam, logInfo, e);
            } catch (Throwable e) {
                FilterCollectorDecorator.getInstance().doException(collectorParam, logInfo, e);
            } finally {
                LOG.debug("exit doFilter dolog");
            }
        } else {
            chain.doFilter(request, response);
            LOG.debug("exit doFilter not dolog");
        }
    }

    /**
     * @Title: getCollectorParam
     * @Description: TODO(初始化日志配置)
     * @returnType: CollectorParam
     * @return
     */
    @SuppressWarnings("unchecked")
    private CollectorParam getCollectorParam() {
        Class<? extends CollectorInterface> collectorClass = null;
        try {
            if (StringUtils.isNotBlank(filterConfig.getInitParameter(COLLECTOR_CLASS))) {
                collectorClass = (Class<? extends CollectorInterface>) Class.forName(filterConfig
                                .getInitParameter(COLLECTOR_CLASS));
            }
        } catch (ClassNotFoundException e) {
            LOG.error("LoadClass {}", e);
        }
        CollectorParam collectorParam = new CollectorParam();
        collectorParam.setTokenGroup(VmonitorContext.getTokenGroup());
        collectorParam.setDoLogClass(new DoLogClass(filterConfig.getInitParameter(KEY), FilterCollectorDecorator
                        .getInstance().getFilterCollector(collectorClass).getClass(), filterConfig
                        .getInitParameter(BEHAVIOR_TYPE), filterConfig.getInitParameter(VALUE)));
        try {
            collectorParam.setMethod(ConfigLogFilter.class.getMethod("doFilter", ServletRequest.class,
                            ServletResponse.class, FilterChain.class));
        } catch (SecurityException e) {
            LOG.error("getMethod", e);
        } catch (NoSuchMethodException e) {
            LOG.error("getMethod", e);
        }
        return collectorParam;
    }

    public void destroy() {
        this.filterConfig = null;
    }

}
