package com.baidu.vmonitor.collect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.logger.DefaultVMonitorLogger;
import com.baidu.vmonitor.model.LogInfo;
import com.baidu.vmonitor.utils.GsonUtils;

public class DefaultCollect implements CollectInterface<LogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultCollect.class);

    private static DefaultCollect instance = null;

    private DefaultVMonitorLogger vMonitorLogger;

    private DefaultCollect(DefaultVMonitorLogger vMonitorLogger) {
        this.vMonitorLogger = vMonitorLogger;
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new DefaultCollect(new DefaultVMonitorLogger());
        }
    }

    public static DefaultCollect getInstance() {
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
            vMonitorLogger.doLog(GsonUtils.getInstance().toJson(info));
        } catch (Exception e) {
            LOG.debug("Gson falurie");
            vMonitorLogger.doLog(info.toString());
        }
    }

}
