package com.baidu.vmonitor.annotation;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.baidu.vmonitor.common.VmonitorContext;

public final class CollectorParam {

    /* 日志分组，用来标识一组日志 */
    private String tokenGroup;

    /* 日志注解 */
    private DoLog doLog;

    /* 日志注解的模拟类 */
    private DoLogClass doLogClass;

    /* Spring AOP 拦截参数ProceedingJoinPoint对象实例 */
    private ProceedingJoinPoint proceedingJoinPoint;

    /* 被拦截的方法实例 */
    private Method method;

    /* 被拦截的方法参数 */
    private Object[] parameters;

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */
    public CollectorParam() {
        super();
        this.tokenGroup = VmonitorContext.getTokenGroup();
    }

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:Spring AOP专用
     * </p>
     * 
     * @param doLog
     * @param proceedingJoinPoint
     */
    public CollectorParam(DoLog doLog, ProceedingJoinPoint proceedingJoinPoint) {
        super();
        this.tokenGroup = VmonitorContext.getTokenGroup();
        this.doLog = doLog;
        this.doLogClass = new DoLogClass(doLog);
        this.proceedingJoinPoint = proceedingJoinPoint;
        this.method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        this.parameters = proceedingJoinPoint.getArgs();
    }

    public String getTokenGroup() {
        return tokenGroup;
    }

    public void setTokenGroup(String tokenGroup) {
        this.tokenGroup = tokenGroup;
    }

    public DoLog getDoLog() {
        return doLog;
    }

    public void setDoLog(DoLog doLog) {
        this.doLog = doLog;
    }

    public DoLogClass getDoLogClass() {
        return doLogClass;
    }

    public void setDoLogClass(DoLogClass doLogClass) {
        this.doLogClass = doLogClass;
    }

    public ProceedingJoinPoint getProceedingJoinPoint() {
        return proceedingJoinPoint;
    }

    public void setProceedingJoinPoint(ProceedingJoinPoint proceedingJoinPoint) {
        this.proceedingJoinPoint = proceedingJoinPoint;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
