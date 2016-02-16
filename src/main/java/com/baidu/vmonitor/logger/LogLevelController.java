package com.baidu.vmonitor.logger;

import org.apache.commons.lang3.StringUtils;

import com.baidu.vmonitor.common.config.CustomCollectorConfig;
import com.baidu.vmonitor.common.config.CustomCollectorConfigKey;


public class LogLevelController {
    
    public static String getLogLevel(String level) {
        if (StringUtils.isBlank(level)) {
            return CustomCollectorConfig.getInstance().getString(CustomCollectorConfigKey.LOG_LEVEL);
        }
        return level;
    }

}
