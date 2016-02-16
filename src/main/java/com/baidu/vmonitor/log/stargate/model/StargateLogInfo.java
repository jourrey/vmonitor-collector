package com.baidu.vmonitor.log.stargate.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.baidu.vmonitor.model.LogInfo;

/**
 * 日志信息
 * 
 * @author liuzheng03
 */
public class StargateLogInfo extends LogInfo implements Serializable {
    /* 序列化唯一ID号 */
    private static final long serialVersionUID = -7199632536836205097L;

    // ~~~ 日志记录信息
    /* 记日志系统所处角色 */
    private Role role;
    /* 记日志系统名称 */
    private String appID;

    // ~~~ 调用服务信息
    /* 调用上下文ID */
    private String contextID;
    /* 调用会话ID */
    private String sessionID;
    /* 调用方系统名 */
    private String callerName;
    /* 调用方IP */
    private String callerIP;
    /* 被用方IP */
    private String calleeIP;
    /* 被调方系统名 */
    private String calleeName;
    /* 调用服务名 */
    private String serviceName;
    /* 调用方法名 */
    private String methodName;
    /* 调用参数 */
    private Object[] Parameters;
    /* 返回结果 */
    private Object result;
    /* 调用服务分组 */
    private String group;
    /* 调用服务版本 */
    private String version;

    // ~~~ 调用状态信息
    /* 发起调用时间 */
    private Date callTime;
    /* 处理耗时 */
    private long consumingTime;
    /* 是否成功 */
    private boolean isSuccess;
    /* 异常信息 */
    private String exceptionMessage;

    /**
     * 无参构造器
     */
    public StargateLogInfo() {
        super();
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAppID() {
        return appID;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public String getContextID() {
        return contextID;
    }

    public void setContextID(String contextID) {
        this.contextID = contextID;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getCallerName() {
        return callerName;
    }

    public void setCallerName(String callerName) {
        this.callerName = callerName;
    }

    public String getCallerIP() {
        return callerIP;
    }

    public void setCallerIP(String callerIP) {
        this.callerIP = callerIP;
    }

    public String getCalleeIP() {
        return calleeIP;
    }

    public void setCalleeIP(String calleeIP) {
        this.calleeIP = calleeIP;
    }

    public String getCalleeName() {
        return calleeName;
    }

    public void setCalleeName(String calleeName) {
        this.calleeName = calleeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

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

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getCallTime() {
        return callTime;
    }

    public void setCallTime(Date callTime) {
        this.callTime = callTime;
    }

    public long getConsumingTime() {
        return consumingTime;
    }

    public void setConsumingTime(long consumingTime) {
        this.consumingTime = consumingTime;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("role", role).append("appID", appID).append("contextID", contextID)
                        .append("sessionID", sessionID).append("callerName", callerName).append("callerIP", callerIP)
                        .append("calleeIP", calleeIP).append("calleeName", calleeName)
                        .append("serviceName", serviceName).append("methodName", methodName)
                        .append("Parameters", Parameters).append("result", result).append("group", group)
                        .append("version", version).append("callTime", callTime).append("consumingTime", consumingTime)
                        .append("isSuccess", isSuccess).append("exceptionMessage", exceptionMessage).toString();
    }

    /**
     * 按Noah格式输出日志.
     */
    public String toNoahString() {
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat formet = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");

        // 前9项，供OP监控用
        sb.append(formet.format(this.callTime) + ",");
        sb.append(formet.format(new Date(this.callTime.getTime() + this.consumingTime)) + ","); // 开始时间+耗时，就是结束时间
        sb.append(this.sessionID + ",");
        sb.append(this.contextID + ",");
        sb.append(this.group + ":");
        sb.append(this.serviceName + "."); // 是. 不是,
        sb.append(this.methodName + ":");
        sb.append(this.version + ",");
        if (this.isSuccess) {
            sb.append("Y,");
        } else {
            sb.append("N,");
        }
        // 后7项，框架自定义信息
        sb.append(this.consumingTime + "ms,");
        sb.append(this.role + ",");
        // sb.append(this.callerName + ",");
        sb.append(this.callerIP + ",");
        // sb.append(this.calleeName + ",");
        sb.append(this.calleeIP + ",");
        if (this.isSuccess) {
            sb.append(",");
        } else {
            sb.append(this.exceptionMessage + ",");
        }

        return sb.toString();
    }

    /**
     * 服务角色枚举.
     * <p>
     * 用于标识服务调用中的调用方、被调方
     * 
     * @author zhengjiaqing
     * @version $Id: Footprint.java, v 0.1 2012-3-23 上午11:20:39 zhengjiaqing Exp
     *          $
     */
    public enum Role {
        /* 调用方 */
        Caller,
        /* 被调方 */
        Callee;
    }

}
