package com.baidu.vmonitor.log.javassist;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.ClassUtils;

import com.baidu.vmonitor.annotation.DoLog;
import com.baidu.vmonitor.common.ParseAnnotation;
import com.baidu.vmonitor.common.ParsePackage;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public class AopDoLogHandler implements AopHandler {
    
    private static final Logger LOG = LoggerFactory.getLogger(AopDoLogHandler.class);
    
    public void doHandler() {
        String packageName = "com.baidu.*.log.**";
        List<String> classNames = ParsePackage.getClassNames(packageName);
        for (String className : classNames) {
            Map<Method, DoLog> map = new HashMap<Method, DoLog>();
            try {
                Class<?> clazz = ClassUtils.getClass(className);
                ParseAnnotation.parseType(clazz, DoLog.class, map);
                ParseAnnotation.parseMethod(clazz, DoLog.class, map);
                for (Iterator<Entry<Method, DoLog>> iterator = map.entrySet().iterator(); iterator.hasNext();) {
                    Entry<Method, DoLog> entry = iterator.next();
                    JavassistWeaving.doWeaving(entry.getKey().getDeclaringClass().getName(), entry.getKey().getName());
                }
            } catch (ClassNotFoundException e) {
                LOG.debug(e.getMessage(), e);
            }
        }
    }
    
    public static void main(String[] args) {
        AopDoLogHandler handler = new AopDoLogHandler();
        StringB sb = new StringB();
        sb.buildString(8);
        System.out.println("===================start");
        handler.doHandler();
        System.out.println("===================end");
        sb.buildString(8);
    }
    
}
