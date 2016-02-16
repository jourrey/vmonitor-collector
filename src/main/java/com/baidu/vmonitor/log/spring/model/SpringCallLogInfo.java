package com.baidu.vmonitor.log.spring.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.baidu.vmonitor.model.CallLogInfo;

/**
 * @ClassName: SpringCallLogInfo
 * @Description: TODO(日志请求信息)
 * @author liuzheng03
 * @date 2014-4-2 下午2:59:44
 */
public class SpringCallLogInfo extends CallLogInfo implements Serializable {

    /* 序列化唯一ID号 */
    private static final long serialVersionUID = -7199632536836205097L;

    /* 调用方法名 */
    private String methodName;
    /* 调用参数 */
    private Object[] Parameters;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return Parameters;
    }

    public void setParameters(Object[] parameters) {
        Parameters = parameters;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("methodName", methodName).append("Parameters", Parameters).toString();
    }

}
