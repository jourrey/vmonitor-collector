package com.baidu.vmonitor.log.struts2.collector;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;
import com.baidu.vmonitor.log.struts2.collect.Struts2LogInfoCollect;
import com.baidu.vmonitor.log.struts2.model.Struts2AnswerLogInfo;
import com.baidu.vmonitor.log.struts2.model.Struts2CallLogInfo;
import com.opensymphony.xwork2.ActionInvocation;

public class Struts2LogInfoCollectorDecorator implements Struts2LogInfoCollectorInterface<Struts2CallLogInfo, Struts2AnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(Struts2LogInfoCollectorDecorator.class);
    
    private static Struts2LogInfoCollectorDecorator instance = null;
    private Struts2LogInfoCollectorInterface<Struts2CallLogInfo, Struts2AnswerLogInfo> struts2LogInfoCollectorInterface;
    
    @SuppressWarnings("unchecked")
    private Struts2LogInfoCollectorDecorator() {
        String logStruts2Collector = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_STRUTS2_COLLECTOR);
        if (StringUtils.isNotBlank(logStruts2Collector)) {
            try {
                Object object = ClassUtils.getClass(logStruts2Collector).newInstance();
                if (null != object && object instanceof Struts2LogInfoCollectorInterface) {
                    struts2LogInfoCollectorInterface = (Struts2LogInfoCollectorInterface<Struts2CallLogInfo, Struts2AnswerLogInfo>) object;
                    LOG.debug("Struts2LogInfoCollector use custom");
                }
            } catch (Exception e) {
                LOG.debug("Struts2LogInfoCollector use custom failure", e);
            }
        }
        if (null == struts2LogInfoCollectorInterface) {
            struts2LogInfoCollectorInterface = new Struts2LogInfoCollector();
            LOG.debug("Struts2LogInfoCollector use vmonitor");
        }
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new Struts2LogInfoCollectorDecorator();
        }
    }

    public static Struts2LogInfoCollectorDecorator getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }
    
    public Struts2CallLogInfo doCall(ActionInvocation invocation) {
        try {
            Struts2CallLogInfo callLogInfo = struts2LogInfoCollectorInterface.doCall(invocation);
            Struts2LogInfoCollect.getInstance().doCollect(callLogInfo);
            return callLogInfo;
        } catch (Exception e) {
            LOG.debug("doCollect failure", e);
            return null;
        }
    }
    
    public Struts2AnswerLogInfo doAnswer(Struts2CallLogInfo logInfo, ActionInvocation invocation) {
        try {
            Struts2AnswerLogInfo answerLogInfo = struts2LogInfoCollectorInterface.doAnswer(logInfo, invocation, null);
            Struts2LogInfoCollect.getInstance().doCollect(answerLogInfo);
            return answerLogInfo;
        } catch (Exception e) {
            LOG.debug("doCollect failure", e);
            return null;
        }
    }

    public Struts2AnswerLogInfo doAnswer(Struts2CallLogInfo logInfo, ActionInvocation invocation, Throwable throwable) {
        try {
            Struts2AnswerLogInfo answerLogInfo = struts2LogInfoCollectorInterface.doAnswer(logInfo, invocation, throwable);
            Struts2LogInfoCollect.getInstance().doCollect(answerLogInfo);
            return answerLogInfo;
        } catch (Exception e) {
            LOG.debug("doCollect failure", e);
            return null;
        }
    }

}
