package com.baidu.vmonitor.log.stargate.collect.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.fengchao.stargate.common.Response;
import com.baidu.fengchao.stargate.common.URI;
import com.baidu.fengchao.stargate.common.constants.Constants;
import com.baidu.fengchao.stargate.remoting.RpcContext;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfig;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfigKey;
import com.baidu.vmonitor.log.stargate.collect.StargateLogInfoCollect;
import com.baidu.vmonitor.log.stargate.collect.StargateLogInfoCollector;
import com.baidu.vmonitor.log.stargate.model.StargateLogInfo;
import com.baidu.vmonitor.log.stargate.model.StargateLogInfo.Role;

/**
 * 服务端日志与客户端不同的是Role和CalleeIP、CalleeName、CallerIP、CallerName
 * @author liuzheng03
 */
public class ServerStargateLogInfoCollector implements StargateLogInfoCollector {
    
    private final Logger LOG = LoggerFactory.getLogger(ServerStargateLogInfoCollector.class);
    
    public static final String FORMAT = VmonitorCollectorConfig.getInstance().getString(VmonitorCollectorConfigKey.LOG_STARGATE_SERVER_FORMAT);
    private static ServerStargateLogInfoCollector instance = null;

    private ServerStargateLogInfoCollector() {
        super();
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new ServerStargateLogInfoCollector();
        }
    }

    public static ServerStargateLogInfoCollector getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }
    
    /* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
    public Object readResolve() {
        return getInstance();
    }
    
    private void fillLogInfo(StargateLogInfo log) {
        RpcContext ctx = RpcContext.getContext();
        URI uri = ctx.getUri();
        
        log.setRole(Role.Callee);
        log.setCalleeIP(ctx.getLocalAddressString());
        log.setCalleeName(ctx.getLocalHostName());
        log.setCallerIP(ctx.getRemoteAddressString());
        log.setCallerName(ctx.getRemoteHostName());
        
        log.setParameters(ctx.getParameters());
        log.setAppID(ctx.getLocalHostName());
        log.setSessionID(ctx.getSessionID());
        log.setGroup(uri.getParameter(Constants.GROUP_KEY));
        log.setVersion(uri.getParameter(Constants.VERSION_KEY));
        log.setMethodName(ctx.getMethodName());
        log.setServiceName(uri.getServiceName());
    }

    public void genericLogInfo(Response response, long begin, long end) {
        LOG.debug("enter ServerStargateLogInfoCollector genericLogInfo");
        StargateLogInfo log = new StargateLogInfo();
        log.setContextID(response.getId());
        log.setCallTime(new Date(begin));
        log.setConsumingTime(end - begin);
//        log.setResult(response.getResult());
        fillLogInfo(log);
        
        if (null != response && response.hasException()) {
            log.setExceptionMessage(response.getException().getMessage());
            log.setSuccess(false);
        } else {
            log.setSuccess(true);
        }

        StargateLogInfoCollect.getInstance().doCollect(FORMAT, log);
    }

}
