package com.baidu.vmonitor.common.spring;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.DoLog;

/**
 * @ClassName: DoLogUtils 
 * @Description: TODO(DoLog注解工具类) 
 * @author liuzheng03 
 * @date 2014-4-9 下午5:38:19 
 */
public class DoLogUtils {
    
    private static final Logger LOG = LoggerFactory.getLogger(DoLogUtils.class);
    
    /**
     * @Title: getClassClazzConvertObject 
     * @Description: TODO(获取日志收集者) 
     * @returnType: Object
     * @param doLog
     * @return
     */
    public static Object getClassClazzConvertObject(DoLog doLog) {
        if (null != doLog) {
            try {
                Class<?> objectClass = doLog.collectorClass();
                if (null != objectClass) {
                    return objectClass.newInstance();
                }
            } catch (Exception e) {
                LOG.debug("DoLogUtils getClassClazzConvertObject failure", e);
                return null;
            }
        }
        return null;
    }
    
    public static String[] getLogInfoKey(DoLog doLog) {
        if (null != doLog) {
            try {
                String[] logInfoKey = doLog.key();
                if (ArrayUtils.isEmpty(logInfoKey)) {
                    return null;
                }
                return logInfoKey;
            } catch (Exception e) {
                LOG.debug("DoLogUtils getLogInfoKey failure", e);
                return null;
            }
        }
        return null;
    }

}
