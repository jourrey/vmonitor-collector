package com.baidu.vmonitor.log.struts2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.log.struts2.collector.Struts2LogInfoCollectorDecorator;
import com.baidu.vmonitor.log.struts2.model.Struts2CallLogInfo;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LogInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = 2351418455263140339L;

    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        LOG.debug("enter LogInterceptor intercept");
        Struts2CallLogInfo logInfo = Struts2LogInfoCollectorDecorator.getInstance().doCall(invocation);
        String result;
        try {
            result = invocation.invoke();
            Struts2LogInfoCollectorDecorator.getInstance().doAnswer(logInfo, invocation);
        } catch (Exception e) {
            Struts2LogInfoCollectorDecorator.getInstance().doAnswer(logInfo, invocation, e);
            throw e;
        } finally {
            LOG.debug("exit LogInterceptor intercept");
        }
        return result;
    }

}
