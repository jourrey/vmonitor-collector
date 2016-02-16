package com.baidu.vmonitor.log.spring.collector;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.log.spring.model.SpringAnswerLogInfo;
import com.baidu.vmonitor.log.spring.model.SpringCallLogInfo;

/**
 * @ClassName: DoLogCollector
 * @Description: TODO(日志收集者)
 * @author liuzheng03
 * @date 2014-4-2 下午2:58:19
 */
public class DoLogCollector implements DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(DoLogCollector.class);

    public SpringCallLogInfo doCall(CollectorParam collectorParam) {
        ProceedingJoinPoint pjp = collectorParam.getProceedingJoinPoint();
        String[] logInfoKey = collectorParam.getDoLog().key();

        SpringCallLogInfo springCallLogInfo = new SpringCallLogInfo();
        springCallLogInfo.setToken(UUID.randomUUID().toString());
        springCallLogInfo.setTokenGroup(collectorParam.getTokenGroup());
        springCallLogInfo.setCallTime(System.currentTimeMillis());
        springCallLogInfo.setMethodName(pjp.getSignature().toLongString());

        /* 用户自选条件处理 */
        if (ArrayUtils.isNotEmpty(logInfoKey)) {
            LOG.debug("DoLogCollector doCall logInfoKey {}", Arrays.toString(logInfoKey));
            List<String> callInfos = Arrays.asList(logInfoKey);
            if (callInfos.contains("PARAMETERS")) {
                springCallLogInfo.setParameters(pjp.getArgs());
            }
        }
        return springCallLogInfo;
    }

    public SpringAnswerLogInfo doAnswer(CollectorParam collectorParam, SpringCallLogInfo logInfo, Object result) {
        String[] logInfoKey = collectorParam.getDoLog().key();

        SpringAnswerLogInfo springAnswerLogInfo = new SpringAnswerLogInfo();
        springAnswerLogInfo.setToken(logInfo.getToken());
        springAnswerLogInfo.setTokenGroup(collectorParam.getTokenGroup());
        springAnswerLogInfo.setAnswerTime(System.currentTimeMillis());
        springAnswerLogInfo.setSuccess(true);

        /* 用户自选条件处理 */
        if (ArrayUtils.isNotEmpty(logInfoKey)) {
            LOG.debug("DoLogCollector doAnswer logInfoKey {}", Arrays.toString(logInfoKey));
            List<String> answerInfos = Arrays.asList(logInfoKey);
            if (answerInfos.contains("RESULT")) {
                springAnswerLogInfo.setResult(result);
            }
            if (answerInfos.contains("CONSUMING_TIME")) {
                springAnswerLogInfo.setConsumingTime(springAnswerLogInfo.getAnswerTime() - logInfo.getCallTime());
            }
        }
        return springAnswerLogInfo;
    }

    public SpringAnswerLogInfo doException(CollectorParam collectorParam, SpringCallLogInfo logInfo, Throwable throwable) {
        String[] logInfoKey = collectorParam.getDoLog().key();

        SpringAnswerLogInfo springAnswerLogInfo = new SpringAnswerLogInfo();
        springAnswerLogInfo.setToken(logInfo.getToken());
        springAnswerLogInfo.setTokenGroup(collectorParam.getTokenGroup());
        springAnswerLogInfo.setAnswerTime(System.currentTimeMillis());
        springAnswerLogInfo.setSuccess(false);

        /* 用户自选条件处理 */
        if (ArrayUtils.isNotEmpty(logInfoKey)) {
            LOG.debug("DoLogCollector doException logInfoKey {}", Arrays.toString(logInfoKey));
            List<String> answerInfos = Arrays.asList(logInfoKey);
            if (answerInfos.contains("EXCEPTION")) {
                springAnswerLogInfo.setExceptionMessage(throwable.getMessage());
            }
        }
        return springAnswerLogInfo;
    }

}
