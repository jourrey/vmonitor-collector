package com.baidu.vmonitor.log.filter.collector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.common.MultipartHttpServletRequest;
import com.baidu.vmonitor.common.VmonitorContext;
import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfig;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfigKey;
import com.baidu.vmonitor.log.filter.model.FilterAnswerLogInfo;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;
import com.baidu.vmonitor.utils.GsonUtils;

/**
 * @deprecated
 * @ClassName: FilterLogInfoCollector 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author liuzheng03 
 * @date 2014-4-28 下午1:28:44 
 */
public class FilterLogInfoCollector implements FilterLogInfoCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(FilterLogInfoCollector.class);

    public FilterCallLogInfo doCall(HttpServletRequest request) {
        FilterCallLogInfo filterCallLogInfo = new FilterCallLogInfo();
        filterCallLogInfo.setToken(UUID.randomUUID().toString());
        filterCallLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());
        filterCallLogInfo.setCallTime(System.currentTimeMillis());
        filterCallLogInfo.setRequestURL(request.getRequestURI());

        /* 用户自选条件处理 */
        String callInfo = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_FILTER_CALL_INFO);
        if (StringUtils.isNotBlank(callInfo)) {
            LOG.debug("Struts2LogInfoCollector use callInfo {}", callInfo);
            List<String> callInfos = Arrays.asList(callInfo.split(VmonitorCollectorConfig.getInstance().getString(
                            VmonitorCollectorConfigKey.LOG_INFO_SPLIT)));
            if (callInfos.contains("serviceName")) {
                filterCallLogInfo.setServiceName(request.getServerName());
            }
            if (callInfos.contains("serviceIP")) {
                filterCallLogInfo.setServiceIP(request.getLocalAddr());
            }
            if (callInfos.contains("servicePort")) {
                filterCallLogInfo.setServicePort(request.getLocalPort());
            }
            if (callInfos.contains("clientIP")) {
                filterCallLogInfo.setClientIP(request.getRemoteAddr());
            }
            if (callInfos.contains("clientPort")) {
                filterCallLogInfo.setClientPort(request.getRemotePort());
            }
            if (callInfos.contains("protocol")) {
                filterCallLogInfo.setProtocol(request.getProtocol());
            }
            if (callInfos.contains("method")) {
                filterCallLogInfo.setMethod(request.getMethod());
            }
            if (callInfos.contains("parameters")) {
                if (MultipartHttpServletRequest.isMultipart(request)) {
                    try {
                        filterCallLogInfo.setParameters(getMultipartFileMap(request));
                    } catch (Exception e) {
                        Map<String, String[]> mapResult = new HashMap<String, String[]>(1);
                        mapResult.put(e.getMessage(), null);
                        filterCallLogInfo.setParameters(mapResult);
                    }
                } else {
                    filterCallLogInfo.setParameters(request.getParameterMap());
                }
            }
        }

        return filterCallLogInfo;
    }

    public FilterAnswerLogInfo doAnswer(FilterCallLogInfo logInfo, HttpServletResponse response, Throwable throwable) {
        FilterAnswerLogInfo filterAnswerLogInfo = new FilterAnswerLogInfo();
        filterAnswerLogInfo.setToken(logInfo.getToken());
        filterAnswerLogInfo.setTokenGroup(logInfo.getTokenGroup());
        filterAnswerLogInfo.setAnswerTime(System.currentTimeMillis());
        if (null == throwable) {
            filterAnswerLogInfo.setSuccess(true);
        } else {
            filterAnswerLogInfo.setSuccess(false);
        }

        /* 用户自选条件处理 */
        String answerInfo = CustomCollectorConfig.getInstance().getString(
                        CustomCollectorConfigKey.LOG_FILTER_ANSWER_INFO);
        if (StringUtils.isNotBlank(answerInfo)) {
            LOG.debug("Struts2LogInfoCollector use answerInfo {}", answerInfo);
            List<String> answerInfos = Arrays.asList(answerInfo.split(VmonitorCollectorConfig.getInstance().getString(
                            VmonitorCollectorConfigKey.LOG_INFO_SPLIT)));
            if (answerInfos.contains("exceptionMessage")) {
                filterAnswerLogInfo.setExceptionMessage(throwable.getMessage());
            }
            if (answerInfos.contains("consumingTime")) {
                filterAnswerLogInfo.setConsumingTime(filterAnswerLogInfo.getAnswerTime() - logInfo.getCallTime());
            }
        }

        return filterAnswerLogInfo;
    }

    private Map<String, String[]> getMultipartFileMap(HttpServletRequest request) throws Exception {
        Map<String, String[]> multipartFilesMap = new HashMap<String, String[]>(1);
        multipartFilesMap.put(
                        "files",
                        new String[] { GsonUtils.getInstance().toJson(
                                        MultipartHttpServletRequest.getMultipartFiles(request)) });
        return multipartFilesMap;
    }

}
