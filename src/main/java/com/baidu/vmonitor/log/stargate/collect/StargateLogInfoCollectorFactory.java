package com.baidu.vmonitor.log.stargate.collect;

import com.baidu.fengchao.stargate.common.Response;
import com.baidu.vmonitor.log.stargate.collect.impl.ClientStargateLogInfoCollector;
import com.baidu.vmonitor.log.stargate.collect.impl.ServerStargateLogInfoCollector;

public class StargateLogInfoCollectorFactory {

    public static void printClientLog(Response response, long begin, long end) {
        ClientStargateLogInfoCollector.getInstance().genericLogInfo(response, begin, end);
    }

    public static void printServerLog(Response response, long begin, long end) {
        ServerStargateLogInfoCollector.getInstance().genericLogInfo(response, begin, end);
    }

}
