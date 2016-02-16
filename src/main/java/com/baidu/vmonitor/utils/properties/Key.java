package com.baidu.vmonitor.utils.properties;

/**
 *  封装访问属性文件的Key,避免直接用String访问PropertyFileReader，强制在配置类中定义常量
 * @author jinzixiang
 *
 */
public interface Key {
    String key();
}
