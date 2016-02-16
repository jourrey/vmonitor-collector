package com.baidu.vmonitor.common.config;

import com.baidu.vmonitor.utils.properties.Key;

public enum VmonitorCollectorConfigKey implements Key {
    LOG_STARGATE_FORMAT("vmonitor.collector.log.stargate.format"),
    LOG_STARGATE_CLIENT_FORMAT("vmonitor.collector.log.stargate.client.format"),
    LOG_STARGATE_SERVER_FORMAT("vmonitor.collector.log.stargate.server.format"),
    LOG_FILTER_FORMAT("vmonitor.collector.log.filter.format"),
    LOG_STRUTS2_FORMAT("vmonitor.collector.log.struts2.format"),
    LOG_SPRING_FORMAT("vmonitor.collector.log.spring.format"),
    LOG_INFO_SPLIT("vmonitor.collector.log.info.split"),
    ;

    private String key;

    private VmonitorCollectorConfigKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

}