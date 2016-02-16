package com.baidu.vmonitor.log.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.log.spring.collector.DoLogCollectorDecorator;
import com.baidu.vmonitor.log.spring.model.SpringCallLogInfo;

/**
 * @ClassName: AspectjInterceptor
 * @Description: TODO(Spring AOP的切面)
 * @author liuzheng03
 * @date 2014-4-2 下午2:15:05
 */
public abstract class AspectjInterceptor {
    
    private static final Logger LOG = LoggerFactory.getLogger(AspectjInterceptor.class);

    /**
     * @Title: advice
     * @Description: TODO(根据doLog处理日志)
     * @returnType: Object
     * @param pjp
     * @param doLog
     * @return
     * @throws Throwable
     */
    public Object advice(ProceedingJoinPoint pjp, DoLog doLog) throws Throwable {
        /* 判断是否启用注解 */
        if (null == doLog || !doLog.value()) {
            return pjp.proceed();
        }
        /* 生成collector参数 */
        CollectorParam collectorParam = null;
        try {
            collectorParam = new CollectorParam(doLog, pjp);
        } catch (Exception e) {
            LOG.error("new CollectorParam failure", e);
        }
        /* 调用请求日志记录 */
        SpringCallLogInfo logInfo = DoLogCollectorDecorator.getInstance().doCall(collectorParam);
        /* 执行代理方法 */
        Object result;
        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            /* 调用异常日志记录 */
            DoLogCollectorDecorator.getInstance().doException(collectorParam, logInfo, e);
            throw e;
        }
        /* 调用应答日志记录 */
        DoLogCollectorDecorator.getInstance().doAnswer(collectorParam, logInfo, result);
        return result;
    }

    /**
     * @Title: aroundAdvice
     * @Description: TODO(处理日志入口方法)
     * @returnType: Object
     * @param pjp
     * @return
     * @throws Throwable
     */
    public abstract Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable;

}
