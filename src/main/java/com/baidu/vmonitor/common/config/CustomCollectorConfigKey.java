package com.baidu.vmonitor.common.config;

import com.baidu.vmonitor.utils.properties.Key;

public enum CustomCollectorConfigKey implements Key {
    LOG_FILTER_COLLECTOR("vmonitor.collector.log.filter.collector"),
    LOG_FILTER_CALL_INFO("vmonitor.collector.log.filter.call.info"),
    LOG_FILTER_ANSWER_INFO("vmonitor.collector.log.filter.answer.info"),
    LOG_STRUTS2_COLLECTOR("vmonitor.collector.log.struts2.collector"),
    LOG_STRUTS2_CALL_INFO("vmonitor.collector.log.struts2.call.info"),
    LOG_STRUTS2_ANSWER_INFO("vmonitor.collector.log.struts2.answer.info"),
    LOG_LEVEL("vmonitor.collector.log.level"),
    LOG_STARGATE_LEVEL("vmonitor.collector.log.stargate.level"),
    LOG_FILTER_LEVEL("vmonitor.collector.log.filter.level"),
    LOG_STRUTS2_LEVEL("vmonitor.collector.log.struts2.level"),
    LOG_SPRING_LEVEL("vmonitor.collector.log.spring.level"),
    ;

    private String key;

    private CustomCollectorConfigKey(String key) {
        this.key = key;
    }

    public String key() {
        return key;
    }

}