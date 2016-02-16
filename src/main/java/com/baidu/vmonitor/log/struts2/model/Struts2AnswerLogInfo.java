package com.baidu.vmonitor.log.struts2.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.baidu.vmonitor.model.AnswerLogInfo;
import com.opensymphony.xwork2.Result;

/**
 * 日志信息
 * 
 * @author liuzheng03
 */
public class Struts2AnswerLogInfo extends AnswerLogInfo implements Serializable {

    /* 序列化唯一ID号 */
    private static final long serialVersionUID = -2350932730609652847L;

    /* 异常信息 */
    private String exceptionMessage;
    /* 返回结果 */
    private Result result;
    /* 执行耗时 */
    private Long consumingTime;

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(Long consumingTime) {
        this.consumingTime = consumingTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("exceptionMessage", exceptionMessage).append("result", result)
                        .append("consumingTime", consumingTime).toString();
    }

}
