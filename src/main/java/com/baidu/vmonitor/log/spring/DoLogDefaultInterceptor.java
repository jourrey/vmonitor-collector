package com.baidu.vmonitor.log.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.log.spring.collector.DoLogCollectorDecorator;

/**
 * @ClassName: DoLogDefaultInterceptor
 * @Description: TODO(Spring AOP的切面)
 * @author liuzheng03
 * @date 2014-4-2 下午2:15:05
 */
@Aspect
public class DoLogDefaultInterceptor extends AspectjInterceptor {

    /**
     * @Title: aspectjMethod
     * @Description: TODO(定义Pointcut，该方法就是一个标识，不进行调用)
     * @returnType: void
     */
    @Pointcut("@annotation(com.baidu.vmonitor.annotation.DoLog)")
    public void aspectjMethod() {
    }

    @Override
    @Around("aspectjMethod()")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        /* 获取日志注解 */
        DoLog doLog = DoLogCollectorDecorator.getInstance().getDoLog(pjp);
        return advice(pjp, doLog);
    }

}
