package com.baidu.vmonitor.log.spring.collect;

import com.baidu.vmonitor.collect.CollectInterface;
import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfig;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfigKey;
import com.baidu.vmonitor.log.spring.AspectjInterceptor;
import com.baidu.vmonitor.logger.DefaultVMonitorLogger;
import com.baidu.vmonitor.model.LogInfo;
import com.baidu.vmonitor.utils.GsonUtils;

/**
 * @ClassName: DoLogCollect 
 * @Description: TODO(日志收集器) 
 * @author liuzheng03 
 * @date 2014-4-2 下午2:57:58 
 */
public class DoLogCollect implements CollectInterface<LogInfo> {

    public static final String LOGLEVEL = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_SPRING_LEVEL);
    public static final Class<?> LOGCATEGORY = AspectjInterceptor.class;
    public static final String FORMAT = VmonitorCollectorConfig.getInstance().getString(VmonitorCollectorConfigKey.LOG_SPRING_FORMAT);

    private static DoLogCollect instance = null;

    private DefaultVMonitorLogger vMonitorLogger;

    private DoLogCollect(DefaultVMonitorLogger vMonitorLogger) {
        this.vMonitorLogger = vMonitorLogger;
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new DoLogCollect(new DefaultVMonitorLogger(LOGCATEGORY, LOGLEVEL));
        }
    }

    public static DoLogCollect getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }

    public void doCollect(LogInfo... info) {
        try {
            vMonitorLogger.doLog(FORMAT, GsonUtils.getInstance().toJson(info));
        } catch (Exception e) {
            vMonitorLogger.doLog(FORMAT, info.toString());
        }
    }

}
