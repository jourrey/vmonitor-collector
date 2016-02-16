package com.baidu.vmonitor.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 日志信息
 * @author liuzheng03
 */
public class CallLogInfo extends LogInfo implements Serializable {
    
    /*序列化唯一ID号 */
    private static final long serialVersionUID = 434879160284236279L;
    
    /*发起调用时间  */
    private Long callTime;
    
    public Long getCallTime() {
        return callTime;
    }
    public void setCallTime(Long callTime) {
        this.callTime = callTime;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("callTime", callTime).toString();
    }
    
}
