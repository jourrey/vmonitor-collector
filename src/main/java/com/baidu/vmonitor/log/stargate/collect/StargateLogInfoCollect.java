package com.baidu.vmonitor.log.stargate.collect;

import com.baidu.vmonitor.collect.CollectInterface;
import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfig;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfigKey;
import com.baidu.vmonitor.log.stargate.model.StargateLogInfo;
import com.baidu.vmonitor.logger.DefaultVMonitorLogger;
import com.baidu.vmonitor.utils.GsonUtils;

public class StargateLogInfoCollect implements CollectInterface<StargateLogInfo> {

    public static final String LOGLEVEL = CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_STARGATE_LEVEL);
    public static final Class<?> LOGCATEGORY = StargateLogInfoCollect.class;
    public static final String FORMAT = VmonitorCollectorConfig.getInstance().getString(VmonitorCollectorConfigKey.LOG_STARGATE_FORMAT);

    private static StargateLogInfoCollect instance = null;

    private DefaultVMonitorLogger vMonitorLogger;

    private StargateLogInfoCollect(DefaultVMonitorLogger vMonitorLogger) {
        this.vMonitorLogger = vMonitorLogger;
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new StargateLogInfoCollect(new DefaultVMonitorLogger(LOGCATEGORY, LOGLEVEL));
        }
    }

    public static StargateLogInfoCollect getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }

    public void doCollect(StargateLogInfo... info) {
        try {
            vMonitorLogger.doLog(FORMAT, GsonUtils.getInstance().toJson(info));
        } catch (Exception e) {
            vMonitorLogger.doLog(FORMAT, info.toString());
        }
    }
    
    public void doCollect(String format, StargateLogInfo... info) {
        try {
            vMonitorLogger.doLog(format, GsonUtils.getInstance().toJson(info));
        } catch (Exception e) {
            vMonitorLogger.doLog(FORMAT, info.toString());
        }
    }

}
