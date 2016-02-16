package com.baidu.vmonitor.log.struts2.collect;

import com.baidu.vmonitor.collect.CollectInterface;
import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfig;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfigKey;
import com.baidu.vmonitor.log.struts2.LogInterceptor;
import com.baidu.vmonitor.logger.DefaultVMonitorLogger;
import com.baidu.vmonitor.model.LogInfo;
import com.baidu.vmonitor.utils.GsonUtils;

public class Struts2LogInfoCollect implements CollectInterface<LogInfo> {

    public static final String LOGLEVEL = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_STRUTS2_LEVEL);
    public static final Class<?> LOGCATEGORY = LogInterceptor.class;
    public static final String FORMAT = VmonitorCollectorConfig.getInstance().getString(VmonitorCollectorConfigKey.LOG_STRUTS2_FORMAT);

    private static Struts2LogInfoCollect instance = null;

    private DefaultVMonitorLogger vMonitorLogger;

    private Struts2LogInfoCollect(DefaultVMonitorLogger vMonitorLogger) {
        this.vMonitorLogger = vMonitorLogger;
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new Struts2LogInfoCollect(new DefaultVMonitorLogger(LOGCATEGORY, LOGLEVEL));
        }
    }

    public static Struts2LogInfoCollect getInstance() {
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
