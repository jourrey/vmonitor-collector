package com.baidu.vmonitor.log.spring.collector;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.BehaviorType;
import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.collector.CollectorInterface;
import com.baidu.vmonitor.common.MethodRepository;
import com.baidu.vmonitor.common.VmonitorContext;
import com.baidu.vmonitor.log.spring.collect.DoLogCollect;
import com.baidu.vmonitor.log.spring.model.SpringAnswerLogInfo;
import com.baidu.vmonitor.log.spring.model.SpringCallLogInfo;
import com.baidu.vmonitor.model.LogInfo;

/**
 * @ClassName: DoLogCollectorDecorator
 * @Description: TODO(日志收集者装饰器)
 * @author liuzheng03
 * @date 2014-4-2 下午2:58:53
 */
public class DoLogCollectorDecorator {

    private static final Logger LOG = LoggerFactory.getLogger(DoLogCollectorDecorator.class);

    private static DoLogCollectorDecorator instance = null;
    private DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo> doLogCollectorInterface;

    private DoLogCollectorDecorator() {
        doLogCollectorInterface = new DoLogCollector();
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new DoLogCollectorDecorator();
        }
    }

    public static DoLogCollectorDecorator getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    /**
     * @Title: readResolve
     * @Description: TODO(如果该对象被用于序列化，可以保证对象在序列化前后保持一致)
     * @returnType: Object
     * @return
     */
    public Object readResolve() {
        return getInstance();
    }

    /**
     * @Title: getDoLog
     * @Description: TODO(获取被代理方法的@DoLog注解)
     * @returnType: DoLog
     * @param pjp
     * @return
     * @throws SecurityException
     * @throws NoSuchMethodException
     */
    public DoLog getDoLog(ProceedingJoinPoint pjp) throws SecurityException, NoSuchMethodException {
        try {
            DoLog doLog = MethodRepository.getInstance().getDoLog(((MethodSignature) pjp.getSignature()).getMethod());
            return doLog;
        } catch (Throwable e) {
            LOG.error("getDoLog failure", e);
        }
        return null;
    }

    /**
     * @Title: getDoLogCollector
     * @Description: TODO(获取日志收集器)
     * @returnType: 
     *              DoLogCollectorInterface<SpringCallLogInfo,SpringAnswerLogInfo
     *              >
     * @param collectorClass
     * @return
     */
    public DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo> getDoLogCollector(
                    Class<? extends CollectorInterface> collectorClass) {
        if (!CollectorInterface.class.equals(collectorClass)) {
            try {
                @SuppressWarnings("unchecked")
                DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo> doLogCollectorInterface = (DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo>) MethodRepository
                                .getInstance().getCollector(collectorClass);
                if (null != doLogCollectorInterface) {
                    LOG.debug("DoLogCollector {}", collectorClass);
                    return doLogCollectorInterface;
                }
            } catch (Throwable e) {
                LOG.error("getDoLogCollectorInterface failure", e);
            }
        }
        return doLogCollectorInterface;
    }

    /**
     * @Title: doCollect
     * @Description: TODO(记录日志)
     * @returnType: void
     * @param doLog
     * @param info
     */
    private void doCollect(DoLog doLog, LogInfo... info) {
        try {
            if (BehaviorType.DO_LOG.equals(doLog.behaviorType())) {
                DoLogCollect.getInstance().doCollect(info);
            }
        } catch (Throwable e) {
            LOG.error("doCollect failure", e);
        }
    }

    public SpringCallLogInfo doCall(CollectorParam collectorParam) {
        SpringCallLogInfo springCallLogInfo = null;
        try {
            springCallLogInfo = getDoLogCollector(collectorParam.getDoLogClass().getCollectorClass()).doCall(
                            collectorParam);
            springCallLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());// 禁止修改
            doCollect(collectorParam.getDoLog(), springCallLogInfo);
        } catch (Throwable e) {
            LOG.error("doCall failure", e);
        }
        return springCallLogInfo;
    }

    public SpringAnswerLogInfo doAnswer(CollectorParam collectorParam, SpringCallLogInfo logInfo, Object result) {
        SpringAnswerLogInfo springAnswerLogInfo = null;
        try {
            springAnswerLogInfo = getDoLogCollector(collectorParam.getDoLogClass().getCollectorClass()).doAnswer(
                            collectorParam, logInfo, result);
            springAnswerLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());// 禁止修改
            doCollect(collectorParam.getDoLog(), springAnswerLogInfo);
        } catch (Throwable e) {
            LOG.error("doAnswer failure", e);
        }
        return springAnswerLogInfo;
    }

    public SpringAnswerLogInfo doException(CollectorParam collectorParam, SpringCallLogInfo logInfo, Throwable throwable) {
        SpringAnswerLogInfo springAnswerLogInfo = null;
        try {
            springAnswerLogInfo = getDoLogCollector(collectorParam.getDoLogClass().getCollectorClass()).doException(
                            collectorParam, logInfo, throwable);
            springAnswerLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());// 禁止修改
            doCollect(collectorParam.getDoLog(), springAnswerLogInfo);
        } catch (Throwable e) {
            LOG.error("doException failure", e);
        }
        return springAnswerLogInfo;
    }

}
