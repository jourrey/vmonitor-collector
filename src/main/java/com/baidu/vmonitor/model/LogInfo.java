package com.baidu.vmonitor.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @ClassName: CollectorParam 
 * @Description: TODO(日志信息基类) 
 * @author liuzheng03 
 * @date 2014-4-11 下午1:35:32 
 */
public class LogInfo implements Serializable {

    /* 序列化唯一ID号 */
    private static final long serialVersionUID = 5155733192627422874L;

    /* 日志编码，用来对映请求日志跟应答日志 */
    private String token;
    /* 日志分组，用来标识一组日志 */
    private String tokenGroup;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenGroup() {
        return tokenGroup;
    }

    public void setTokenGroup(String tokenGroup) {
        this.tokenGroup = tokenGroup;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("token", token).append("tokenGroup", tokenGroup).toString();
    }

}
