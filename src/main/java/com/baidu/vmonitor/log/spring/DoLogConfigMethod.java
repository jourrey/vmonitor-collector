package com.baidu.vmonitor.log.spring;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.collector.CollectorInterface;
import com.baidu.vmonitor.common.ParseAnnotation;
import com.baidu.vmonitor.log.spring.collector.DoLogCollector;
import com.baidu.vmonitor.log.spring.collector.DoLogCollectorInterface;
import com.baidu.vmonitor.log.spring.model.SpringAnswerLogInfo;
import com.baidu.vmonitor.log.spring.model.SpringCallLogInfo;

public class DoLogConfigMethod {

    private static final Logger LOG = LoggerFactory.getLogger(DoLogConfigMethod.class);

    private static DoLogConfigMethod instance = null;

    private final Map<Method, DoLog> doLogResolverCache = new ConcurrentHashMap<Method, DoLog>();

    private final Map<Class<?>, DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo>> doLogCollectorInterfaceResolverCache = new ConcurrentHashMap<Class<?>, DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo>>();

    private DoLogConfigMethod() {
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new DoLogConfigMethod();
        }
    }

    public static DoLogConfigMethod getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }

    /**
     * @Title: getDoLog
     * @Description: TODO(根据方法获取注解)
     * @returnType: DoLog
     * @param method
     * @return
     */
    public DoLog getDoLog(Method method) {
        DoLog doLog = doLogResolverCache.get(method);
        if (null == doLog) {
            doLog = method.getAnnotation(DoLog.class);
            if (null == doLog) {
                initDoLogResolverCache(method.getDeclaringClass());
            } else {
                doLogResolverCache.put(method, doLog);
            }
            doLog = doLogResolverCache.get(method);
        }
        return doLog;
    }

    /**
     * @Title: initMethodResolverCache
     * @Description: TODO(初始化被标注类的方法集合)
     * @returnType: void
     * @param className
     */
    private void initDoLogResolverCache(Class<?> clazz) {
        LOG.debug("initMethodResolverCache {}", clazz.getName());
        ParseAnnotation.parseType(clazz, DoLog.class, doLogResolverCache);
        ParseAnnotation.parseMethod(clazz, DoLog.class, doLogResolverCache);
    }

    /**
     * @Title: getDoLogCollectorInterface
     * @Description: TODO(根据class获取日志收集者)
     * @returnType: 
     *              CollectorInterface<SpringCallLogInfo,SpringAnswerLogInfo
     *              >
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo> getDoLogCollectorInterface(Class<?> clazz)
                    throws InstantiationException, IllegalAccessException {
        DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo> doLogCollectorInterface = null;
        if (DoLogCollectorInterface.class.isAssignableFrom(clazz)) {
            doLogCollectorInterface = doLogCollectorInterfaceResolverCache.get(clazz);
            if (null == doLogCollectorInterface) {
                doLogCollectorInterface = (DoLogCollectorInterface<SpringCallLogInfo, SpringAnswerLogInfo>) clazz
                                .newInstance();
                doLogCollectorInterfaceResolverCache.put(clazz, doLogCollectorInterface);
            }
        }
        return doLogCollectorInterface;
    }

    public static void main(String[] args) {
        DoLogConfigMethod.getInstance().initDoLogResolverCache(DoLogCollector.class);
        for (Iterator<Entry<Method, DoLog>> iterator = DoLogConfigMethod.getInstance().doLogResolverCache.entrySet()
                        .iterator(); iterator.hasNext();) {
            Entry<Method, DoLog> entry = iterator.next();
            System.out.println(entry.getKey().getName());
            System.out.println(entry.getValue().toString());
        }
//        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
//        for (StackTraceElement stackTraceElement2 : stackTraceElement) {
//            System.out.println(stackTraceElement2);
//        }
        if (DoLogConfigMethod.class.equals(new DoLogConfigMethod().getClass())) {
            System.out.println(new DoLogConfigMethod().getClass());
        }
    }

}
