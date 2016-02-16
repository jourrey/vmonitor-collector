package com.baidu.vmonitor.common;

import java.util.UUID;

import com.baidu.vmonitor.annotation.CollectorParam;

/**
 * @ClassName: VmonitorContext 
 * @Description: TODO(日志监控上下文) 
 * @author liuzheng03 
 * @date 2014-4-23 下午6:27:28 
 */
public class VmonitorContext {
    
    private static ThreadLocal<String> vmonitorTokenGroup = new ThreadLocal<String>();
    
    private static ThreadLocal<CollectorParam> vmonitorCollectorParam = new ThreadLocal<CollectorParam>();
    
    public static String getTokenGroup() {
        if (null == vmonitorTokenGroup.get()) {
            vmonitorTokenGroup.set(UUID.randomUUID().toString());
        }
        return vmonitorTokenGroup.get();
    }
    
    public static CollectorParam getCollectorParam() {
        return vmonitorCollectorParam.get();
    }
    
    public static void setCollectorParam(CollectorParam collectorParam) {
        if (null == vmonitorCollectorParam.get()) {
            vmonitorCollectorParam.set(collectorParam);
        }
    }
    
}
