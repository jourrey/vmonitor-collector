package com.baidu.vmonitor.common.config;

import com.baidu.vmonitor.utils.properties.PropertyFileReader;

/**
 * To read properties in {@code message-task.properites}
 * 
 * @author liuzheng03
 * 
 */
public class VmonitorCollectorConfig {

    private static PropertyFileReader<VmonitorCollectorConfigKey> instance = new PropertyFileReader<VmonitorCollectorConfigKey>(
                    "vmonitor-collector-config");

    private VmonitorCollectorConfig() {

    }

    public static PropertyFileReader<VmonitorCollectorConfigKey> getInstance() {
        return instance;
    }

}
