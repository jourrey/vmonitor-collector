package com.baidu.vmonitor.common;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.collector.CollectorInterface;
import com.baidu.vmonitor.log.spring.collector.DoLogCollector;

/**
 * @ClassName: MethodRepository
 * @Description: TODO(日志方法缓存)
 * @author liuzheng03
 * @date 2014-4-25 下午3:59:38
 */
public class MethodRepository {

    private static final Logger LOG = LoggerFactory.getLogger(MethodRepository.class);

    private static MethodRepository instance = null;

    private final Map<Method, DoLog> doLogResolverCache = new ConcurrentHashMap<Method, DoLog>();

    private final Map<Class<? extends CollectorInterface>, CollectorInterface> collectorResolverCache = new ConcurrentHashMap<Class<? extends CollectorInterface>, CollectorInterface>();

    private MethodRepository() {
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new MethodRepository();
        }
    }

    public static MethodRepository getInstance() {
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
     * @Title: getCollector
     * @Description: TODO(根据class获取日志收集者)
     * @returnType: T
     * @param clazz
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public <T extends CollectorInterface> T getCollector(Class<T> clazz) throws InstantiationException,
                    IllegalAccessException {
        T collector = null;
        if (CollectorInterface.class.isAssignableFrom(clazz)) {
            collector = (T) collectorResolverCache.get(clazz);
            if (null == collector) {
                collector = (T) clazz.newInstance();
                collectorResolverCache.put(clazz, collector);
            }
        }
        return collector;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        MethodRepository.getInstance().initDoLogResolverCache(DoLogCollector.class);
        for (Iterator<Entry<Method, DoLog>> iterator = MethodRepository.getInstance().doLogResolverCache.entrySet()
                        .iterator(); iterator.hasNext();) {
            Entry<Method, DoLog> entry = iterator.next();
            System.out.println(entry.getKey().getName());
            System.out.println(entry.getValue().toString());
        }
        if (CollectorInterface.class.isAssignableFrom(DoLogCollector.class)) {
            System.out.println(true);
        }
        System.out.println(MethodRepository.getInstance().getCollector(DoLogCollector.class));
//        System.out.println(MethodRepository.getInstance().getCollector(String.class));
//        for (Iterator<Entry<Class<?>, CollectorInterface<CallLogInfo, AnswerLogInfo>>> iterator = MethodRepository.getInstance().collectorResolverCache.entrySet()
//                        .iterator(); iterator.hasNext();) {
//            Entry<Class<?>, CollectorInterface<CallLogInfo, AnswerLogInfo>> entry = iterator.next();
//            System.out.println(entry.getKey().getName());
//            System.out.println(entry.getValue().toString());
//        }
//        StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();
//        for (StackTraceElement stackTraceElement2 : stackTraceElement) {
//            System.out.println(stackTraceElement2);
//        }
    }

}
