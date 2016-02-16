package com.baidu.vmonitor.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.core.annotation.AnnotationUtils;

public class ParseAnnotation {
    
    /**
     * @Title: parseType 
     * @Description: TODO(解析类上的注解，分配给所有类公共方法) 
     * @returnType: Map<Method,A>
     * @param clazz
     * @param annotationType
     * @param map
     * @return
     */
    public static <A extends Annotation> Map<Method, A> parseType(Class<?> clazz, Class<A> annotationType,
                    Map<Method, A> map) {
        A annotation = AnnotationUtils.findAnnotation(clazz, annotationType);
        if (annotation != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                map.put(method, annotation);
            }
        }
        return map;
    }
    
    /**
     * @Title: parseMethod 
     * @Description: TODO(解析类所有公共方法上的注解) 
     * @returnType: Map<Method,A>
     * @param clazz
     * @param annotationType
     * @param map
     * @return
     */
    public static <A extends Annotation> Map<Method, A> parseMethod(Class<?> clazz, Class<A> annotationType,
                    Map<Method, A> map) {
        for (Method method : clazz.getDeclaredMethods()) {
            A annotation = AnnotationUtils.findAnnotation(method, annotationType);
            if (annotation != null) {
                map.put(method, annotation);
            }
        }
        return map;
    }
    
    /**
     * @Title: parseMethod 
     * @Description: TODO(解析某个方法上的注解，方法没有则获取类上的) 
     * @returnType: A
     * @param method
     * @param annotationType
     * @return
     */
    public static <A extends Annotation> A parseMethod(Method method, Class<A> annotationType, boolean extendClass) {
        A annotation = AnnotationUtils.findAnnotation(method, annotationType);
        if (null == annotation && extendClass) {
            annotation = AnnotationUtils.findAnnotation(method.getDeclaringClass(), annotationType);
        }
        return annotation;
    }
    
}