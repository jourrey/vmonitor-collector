package com.baidu.vmonitor.log.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.log.filter.collector.FilterLogInfoCollectorDecorator;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;

/**
 * @deprecated
 * @ClassName: LogFilter 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author liuzheng03 
 * @date 2014-4-28 上午11:05:12 
 */
public class LogFilter implements Filter {

    private static final Logger LOG = LoggerFactory.getLogger(LogFilter.class);
    
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
                    ServletException {
        LOG.debug("enter LogFilter doFilter");
        FilterCallLogInfo logInfo = FilterLogInfoCollectorDecorator.getInstance().doCall((HttpServletRequest) request);
        try {
            chain.doFilter(request, response);
            FilterLogInfoCollectorDecorator.getInstance().doAnswer(logInfo, (HttpServletResponse) response);
        } catch (IOException e) {
            FilterLogInfoCollectorDecorator.getInstance().doAnswer(logInfo, (HttpServletResponse) response, e);
            throw e;
        } catch (ServletException e) {
            FilterLogInfoCollectorDecorator.getInstance().doAnswer(logInfo, (HttpServletResponse) response, e);
            throw e;
        } catch (Throwable e) {
            FilterLogInfoCollectorDecorator.getInstance().doAnswer(logInfo, (HttpServletResponse) response, e);
            throw new ServletException(e.getMessage());
        } finally {
            LOG.debug("exit LogFilter doFilter");
        }
    }

    public void destroy() {
    }
    
}
