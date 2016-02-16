package com.baidu.vmonitor.log.spring.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.baidu.vmonitor.model.AnswerLogInfo;

/**
 * @ClassName: SpringAnswerLogInfo
 * @Description: TODO(日志应答信息)
 * @author liuzheng03
 * @date 2014-4-2 下午3:00:01
 */
public class SpringAnswerLogInfo extends AnswerLogInfo implements Serializable {

    /* 序列化唯一ID号 */
    private static final long serialVersionUID = -7199632536836205097L;

    /* 执行耗时 */
    private Long consumingTime;
    /* 异常信息 */
    private String exceptionMessage;
    /* 返回结果 */
    private Object result;

    public Long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(Long consumingTime) {
        this.consumingTime = consumingTime;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("consumingTime", consumingTime)
                        .append("exceptionMessage", exceptionMessage).append("result", result).toString();
    }

}
