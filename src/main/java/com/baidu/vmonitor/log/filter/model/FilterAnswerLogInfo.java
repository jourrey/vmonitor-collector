package com.baidu.vmonitor.log.filter.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.baidu.vmonitor.model.AnswerLogInfo;

/**
 * 日志信息
 * 
 * @author liuzheng03
 */
public class FilterAnswerLogInfo extends AnswerLogInfo implements Serializable {

    /* 序列化唯一ID号 */
    private static final long serialVersionUID = -7199632536836205097L;

    /* 异常信息 */
    private String exceptionMessage;
    /* 执行耗时 */
    private Long consumingTime;

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(Long consumingTime) {
        this.consumingTime = consumingTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("exceptionMessage", exceptionMessage)
                        .append("consumingTime", consumingTime).toString();
    }

}
