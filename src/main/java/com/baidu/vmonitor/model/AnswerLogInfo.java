package com.baidu.vmonitor.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 日志信息
 * 
 * @author liuzheng03
 */
public class AnswerLogInfo extends LogInfo implements Serializable {

    /* 序列化唯一ID号 */
    private static final long serialVersionUID = 7321017376601326494L;

    /* 响应时间 */
    private Long answerTime;
    /* 是否成功 */
    private Boolean success;

    public Long getAnswerTime() {
        return answerTime;
    }

    public void setAnswerTime(Long answerTime) {
        this.answerTime = answerTime;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("answerTime", answerTime).append("success", success).toString();
    }

}
