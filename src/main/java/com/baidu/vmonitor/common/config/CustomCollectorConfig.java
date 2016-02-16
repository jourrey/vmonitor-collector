package com.baidu.vmonitor.common.config;

import com.baidu.vmonitor.utils.properties.PropertyFileReaderInexistence;

/**
 * To read properties in {@code message-task.properites}
 * 
 * @author liuzheng03
 * 
 */
public class CustomCollectorConfig {
    
    private static PropertyFileReaderInexistence<CustomCollectorConfigKey> instance = new PropertyFileReaderInexistence<CustomCollectorConfigKey>(
                    "custom-collector-config");

    private CustomCollectorConfig() {

    }

    public static PropertyFileReaderInexistence<CustomCollectorConfigKey> getInstance() {
        return instance;
    }
    
}
