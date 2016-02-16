package com.baidu.vmonitor.log.filter.collector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.common.MultipartHttpServletRequest;
import com.baidu.vmonitor.common.VmonitorContext;
import com.baidu.vmonitor.log.filter.model.FilterAnswerLogInfo;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;
import com.baidu.vmonitor.utils.GsonUtils;

public class FilterCollector implements FilterCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(FilterCollector.class);

    public FilterCallLogInfo doCall(CollectorParam collectorParam) {
        HttpServletRequest request = (HttpServletRequest) collectorParam.getParameters()[0];
        FilterCallLogInfo filterCallLogInfo = new FilterCallLogInfo();
        filterCallLogInfo.setToken(UUID.randomUUID().toString());
        filterCallLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());
        filterCallLogInfo.setCallTime(System.currentTimeMillis());
        filterCallLogInfo.setRequestURL(request.getRequestURI());

        /* 用户自选条件处理 */
        String[] callInfo = collectorParam.getDoLogClass().getKey();
        if (ArrayUtils.isNotEmpty(callInfo)) {
            LOG.debug("Collector use callInfo key {}", Arrays.toString(callInfo));
            List<String> callInfos = Arrays.asList(callInfo);
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

    public FilterAnswerLogInfo doAnswer(CollectorParam collectorParam, FilterCallLogInfo logInfo, Object result) {
        FilterAnswerLogInfo filterAnswerLogInfo = new FilterAnswerLogInfo();
        filterAnswerLogInfo.setToken(logInfo.getToken());
        filterAnswerLogInfo.setTokenGroup(logInfo.getTokenGroup());
        filterAnswerLogInfo.setAnswerTime(System.currentTimeMillis());
        filterAnswerLogInfo.setSuccess(true);

        /* 用户自选条件处理 */
        String[] answerInfo = collectorParam.getDoLogClass().getKey();
        if (ArrayUtils.isNotEmpty(answerInfo)) {
            LOG.debug("collector use answerInfo key {}", Arrays.toString(answerInfo));
            List<String> answerInfos = Arrays.asList(answerInfo);
            if (answerInfos.contains("consumingTime")) {
                filterAnswerLogInfo.setConsumingTime(filterAnswerLogInfo.getAnswerTime() - logInfo.getCallTime());
            }
        }

        return filterAnswerLogInfo;
    }

    public FilterAnswerLogInfo doException(CollectorParam collectorParam, FilterCallLogInfo logInfo, Throwable throwable) {
        FilterAnswerLogInfo filterAnswerLogInfo = new FilterAnswerLogInfo();
        filterAnswerLogInfo.setToken(logInfo.getToken());
        filterAnswerLogInfo.setTokenGroup(logInfo.getTokenGroup());
        filterAnswerLogInfo.setAnswerTime(System.currentTimeMillis());
        filterAnswerLogInfo.setSuccess(false);

        /* 用户自选条件处理 */
        String[] exceptionInfo = collectorParam.getDoLogClass().getKey();
        if (ArrayUtils.isNotEmpty(exceptionInfo)) {
            LOG.debug("collector use exceptionInfo key {}", Arrays.toString(exceptionInfo));
            List<String> exceptionInfos = Arrays.asList(exceptionInfo);
            if (exceptionInfos.contains("exceptionMessage")) {
                filterAnswerLogInfo.setExceptionMessage(throwable.getMessage());
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
