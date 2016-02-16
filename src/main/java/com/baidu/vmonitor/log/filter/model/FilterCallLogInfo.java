package com.baidu.vmonitor.log.filter.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.baidu.vmonitor.model.CallLogInfo;

/**
 * 日志信息
 * 
 * @author liuzheng03
 */
public class FilterCallLogInfo extends CallLogInfo implements Serializable {

    /* 序列化唯一ID号 */
    private static final long serialVersionUID = -7199632536836205097L;

    /* 调用URL */
    private String requestURL;
    /* 记日志系统名称 */
    private String serviceName;
    /* 被用方IP */
    private String serviceIP;
    /* 被用方IP */
    private Integer servicePort;
    /* 调用方IP */
    private String clientIP;
    /* 调用方IP */
    private Integer clientPort;
    /* 调用协议 HTTP */
    private String protocol;
    /* 调用类型 GET/POST */
    private String method;
    /* 调用参数 */
    private Map<String, String[]> Parameters;

    public String getRequestURL() {
        return requestURL;
    }

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceIP() {
        return serviceIP;
    }

    public void setServiceIP(String serviceIP) {
        this.serviceIP = serviceIP;
    }

    public Integer getServicePort() {
        return servicePort;
    }

    public void setServicePort(Integer servicePort) {
        this.servicePort = servicePort;
    }

    public String getClientIP() {
        return clientIP;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public Integer getClientPort() {
        return clientPort;
    }

    public void setClientPort(Integer clientPort) {
        this.clientPort = clientPort;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Map<String, String[]> getParameters() {
        return Parameters;
    }

    public void setParameters(Map<String, String[]> parameters) {
        Parameters = parameters;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requestURL", requestURL).append("serviceName", serviceName)
                        .append("serviceIP", serviceIP).append("servicePort", servicePort).append("clientIP", clientIP)
                        .append("clientPort", clientPort).append("protocol", protocol).append("method", method)
                        .append("Parameters", Parameters).toString();
    }

}
