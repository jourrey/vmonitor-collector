package com.baidu.vmonitor.log.spring;

import org.aspectj.lang.ProceedingJoinPoint;

import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.log.spring.collector.DoLogCollectorDecorator;

/**
 * @ClassName: DoLogExpandInterceptor
 * @Description: TODO(Spring AOP的切面)
 * @author liuzheng03
 * @date 2014-4-2 下午2:15:05
 */
public class DoLogExpandInterceptor extends AspectjInterceptor {

    @Override
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        /* 获取日志注解 */
        DoLog doLog = DoLogCollectorDecorator.getInstance().getDoLog(pjp);
        return advice(pjp, doLog);
    }

}
