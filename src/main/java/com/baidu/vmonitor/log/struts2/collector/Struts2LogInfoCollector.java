package com.baidu.vmonitor.log.struts2.collector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.common.MultipartHttpServletRequest;
import com.baidu.vmonitor.common.VmonitorContext;
import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfig;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfigKey;
import com.baidu.vmonitor.log.struts2.model.Struts2AnswerLogInfo;
import com.baidu.vmonitor.log.struts2.model.Struts2CallLogInfo;
import com.baidu.vmonitor.utils.GsonUtils;
import com.opensymphony.xwork2.ActionInvocation;

public class Struts2LogInfoCollector implements Struts2LogInfoCollectorInterface<Struts2CallLogInfo, Struts2AnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(Struts2LogInfoCollector.class);

    public Struts2CallLogInfo doCall(ActionInvocation invocation) {
        HttpServletRequest request = ServletActionContext.getRequest();
        Struts2CallLogInfo struts2CallLogInfo = new Struts2CallLogInfo();
        struts2CallLogInfo.setToken(UUID.randomUUID().toString());
        struts2CallLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());
        struts2CallLogInfo.setCallTime(System.currentTimeMillis());
        struts2CallLogInfo.setRequestURL(request.getRequestURI());
        
        /*用户自选条件处理*/
        String callInfo = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_STRUTS2_CALL_INFO);
        if(StringUtils.isNotBlank(callInfo)) {
            LOG.debug("Struts2LogInfoCollector use callInfo {}", callInfo);
            List<String> callInfos = Arrays.asList(callInfo.split(VmonitorCollectorConfig.getInstance().getString(VmonitorCollectorConfigKey.LOG_INFO_SPLIT)));
            if (callInfos.contains("serviceName")) {
                struts2CallLogInfo.setServiceName(request.getServerName());
            }
            if (callInfos.contains("serviceIP")) {
                struts2CallLogInfo.setServiceIP(request.getLocalAddr());
            }
            if (callInfos.contains("servicePort")) {
                struts2CallLogInfo.setServicePort(request.getLocalPort());
            }
            if (callInfos.contains("clientIP")) {
                struts2CallLogInfo.setClientIP(request.getRemoteAddr());
            }
            if (callInfos.contains("clientPort")) {
                struts2CallLogInfo.setClientPort(request.getRemotePort());
            }
            if (callInfos.contains("protocol")) {
                struts2CallLogInfo.setProtocol(request.getProtocol());
            }
            if (callInfos.contains("method")) {
                struts2CallLogInfo.setMethod(request.getMethod());
            }
            if (callInfos.contains("className")) {
                struts2CallLogInfo.setMethod(request.getMethod());
            }
            if (callInfos.contains("methodName")) {
                struts2CallLogInfo.setMethod(request.getMethod());
            }
            if (callInfos.contains("parameters")) {
                if (MultipartHttpServletRequest.isMultipart(request)) {
                    try {
                        struts2CallLogInfo.setParameters(getMultipartFileMap(request));
                    } catch (Exception e) {
                        Map<String, Object> mapResult=new HashMap<String, Object>(1);    
                        mapResult.put(e.getMessage(), null);
                        struts2CallLogInfo.setParameters(mapResult);
                    }
                } else {
                    struts2CallLogInfo.setParameters(invocation.getInvocationContext().getParameters());
                }
            }
        }
        
        return struts2CallLogInfo;
    }

    public Struts2AnswerLogInfo doAnswer(Struts2CallLogInfo logInfo, ActionInvocation invocation, Throwable throwable) {
        Struts2AnswerLogInfo struts2AnswerLogInfo = new Struts2AnswerLogInfo();
        struts2AnswerLogInfo.setToken(logInfo.getToken());
        struts2AnswerLogInfo.setTokenGroup(logInfo.getTokenGroup());
        struts2AnswerLogInfo.setAnswerTime(System.currentTimeMillis());
        if (null == throwable) {
            struts2AnswerLogInfo.setSuccess(true);
        } else {
            struts2AnswerLogInfo.setSuccess(false);
        }
        
        /*用户自选条件处理*/
        String answerInfo = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_STRUTS2_ANSWER_INFO);
        if(StringUtils.isNotBlank(answerInfo)) {
            LOG.debug("Struts2LogInfoCollector use answerInfo {}", answerInfo);
            List<String> answerInfos = Arrays.asList(answerInfo.split(VmonitorCollectorConfig.getInstance().getString(VmonitorCollectorConfigKey.LOG_INFO_SPLIT)));
            if (answerInfos.contains("exceptionMessage")) {
                struts2AnswerLogInfo.setExceptionMessage(throwable.getMessage());
            }
            if (answerInfos.contains("consumingTime")) {
                struts2AnswerLogInfo.setConsumingTime(struts2AnswerLogInfo.getAnswerTime() - logInfo.getCallTime());
            }
            if (answerInfos.contains("result") && null != invocation) {
                try {
                    struts2AnswerLogInfo.setResult(invocation.getResult());
                } catch (Exception ex) {
                    struts2AnswerLogInfo.setResult(null);
                }
            }
        }
        
        return struts2AnswerLogInfo;
    }
    
    private Map<String, Object> getMultipartFileMap(HttpServletRequest request) throws Exception {
        Map<String, Object> multipartFilesMap = new HashMap<String, Object>(1);
        multipartFilesMap.put("files",
                        GsonUtils.getInstance().toJson(MultipartHttpServletRequest.getMultipartFiles(request)));
        return multipartFilesMap;
    }

}
