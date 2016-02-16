package com.baidu.vmonitor.log.filter.collector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;
import com.baidu.vmonitor.log.filter.collect.FilterCollect;
import com.baidu.vmonitor.log.filter.model.FilterAnswerLogInfo;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;

/**
 * @deprecated
 * @ClassName: FilterLogInfoCollectorDecorator 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author liuzheng03 
 * @date 2014-4-28 下午1:29:04 
 */
public class FilterLogInfoCollectorDecorator implements FilterLogInfoCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(FilterLogInfoCollectorDecorator.class);
    
    private static FilterLogInfoCollectorDecorator instance = null;
    private FilterLogInfoCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> filterLogInfoCollectorInterface;
    
    @SuppressWarnings("unchecked")
    private FilterLogInfoCollectorDecorator() {
        String logFilterCollector = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_FILTER_COLLECTOR);
        if (StringUtils.isNotBlank(logFilterCollector)) {
            try {
                Object object = ClassUtils.getClass(logFilterCollector).newInstance();
                if (null != object && object instanceof FilterLogInfoCollectorInterface) {
                    filterLogInfoCollectorInterface = (FilterLogInfoCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo>) object;
                    LOG.debug("Struts2LogInfoCollector use custom");
                }
            } catch (Exception e) {
                LOG.debug("Struts2LogInfoCollector use custom failure", e);
            }
        }
        if (null == filterLogInfoCollectorInterface) {
            filterLogInfoCollectorInterface = new FilterLogInfoCollector();
            LOG.debug("Struts2LogInfoCollector use vmonitor");
        }
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new FilterLogInfoCollectorDecorator();
        }
    }

    public static FilterLogInfoCollectorDecorator getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }
    
    public FilterCallLogInfo doCall(HttpServletRequest request) {
        try {
            FilterCallLogInfo callLogInfo = filterLogInfoCollectorInterface.doCall(request);
            FilterCollect.getInstance().doCollect(callLogInfo);
            return callLogInfo;
        } catch (Exception e) {
            LOG.debug("doCollect failure", e);
            return null;
        }
        
    }
    
    public FilterAnswerLogInfo doAnswer(FilterCallLogInfo logInfo, HttpServletResponse response) {
        try {
            FilterAnswerLogInfo answerLogInfo = filterLogInfoCollectorInterface.doAnswer(logInfo, response, null);
            FilterCollect.getInstance().doCollect(answerLogInfo);
            return answerLogInfo;
        } catch (Exception e) {
            LOG.debug("doCollect failure", e);
            return null;
        }
    }
    
    public FilterAnswerLogInfo doAnswer(FilterCallLogInfo logInfo, HttpServletResponse response, Throwable throwable) {
        try {
            FilterAnswerLogInfo answerLogInfo = filterLogInfoCollectorInterface.doAnswer(logInfo, response, throwable);
            FilterCollect.getInstance().doCollect(answerLogInfo);
            return answerLogInfo;
        } catch (Exception e) {
            LOG.debug("doCollect failure", e);
            return null;
        }
    }
    
}
