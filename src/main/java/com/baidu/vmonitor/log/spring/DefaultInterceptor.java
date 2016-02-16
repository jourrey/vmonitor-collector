package com.baidu.vmonitor.log.spring;

import org.aspectj.lang.ProceedingJoinPoint;

import com.baidu.vmonitor.annotation.DoLog;

/**
 * @ClassName: DefaultInterceptor
 * @Description: TODO(Spring AOP的切面)
 * @author liuzheng03
 * @date 2014-4-2 下午2:15:05
 */
public class DefaultInterceptor extends AspectjInterceptor {

    /**
     * @Title: aspectjMethod
     * @Description: TODO(承载一个默认的@DoLog)
     * @returnType: void
     */
    @DoLog
    public void aspectjMethod() {
    }

    @Override
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        /* 获取日志注解 */
        DoLog doLog = DefaultInterceptor.class.getMethod("aspectjMethod").getAnnotation(DoLog.class);
        return advice(pjp, doLog);
    }
    
}
