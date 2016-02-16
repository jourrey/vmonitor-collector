package com.baidu.vmonitor.log.stargate.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.fengchao.stargate.common.Callback;
import com.baidu.fengchao.stargate.common.Request;
import com.baidu.fengchao.stargate.common.Requestor;
import com.baidu.fengchao.stargate.common.Response;
import com.baidu.fengchao.stargate.remoting.Filter;
import com.baidu.fengchao.stargate.remoting.LocalContext;
import com.baidu.fengchao.stargate.remoting.exceptions.RpcException;
import com.baidu.vmonitor.log.stargate.collect.StargateLogInfoCollectorFactory;

/**
 * 处理stargate client日志本地化
 * 
 * @注意 
 *     类名不可随意更改，与src/main/resources/META-INF/services/com.baidu.fengchao.stargate
 *     .remoting.Filter文件中，配置绑定了，如要修改请一起修改。并告知使用方更改filter配置，影响较大建议不要修改
 * @author liuzheng03
 * 
 */
public class VMonitorStargateClientFilter implements Filter {
    private final Logger LOG = LoggerFactory.getLogger(VMonitorStargateClientFilter.class);

    private static final String STARGATE_REQUEST_TIMEMILLIS_KEY = "VMonitorStargateClientRequestTimeMillis";

    public VMonitorStargateClientFilter() {
    }

    public void handleResult(Response result) {
        LOG.debug("enter VMonitorStargateClientFilter handleResult");
        long end = System.currentTimeMillis();
        Long begin = LocalContext.getContext(result.getId()).get(STARGATE_REQUEST_TIMEMILLIS_KEY);
        StargateLogInfoCollectorFactory.printClientLog(result, begin, end);
    }

    public void handleError(Throwable error) {

    }

    public void request(Requestor<?> requestor, Request request, Callback<Response> callback) throws RpcException {
        LOG.debug("enter VMonitorStargateClientFilter request");
        long begin = System.currentTimeMillis();
        LocalContext.getContext(request.getId()).set(STARGATE_REQUEST_TIMEMILLIS_KEY, begin);
        requestor.request(request, callback);
    }
}
