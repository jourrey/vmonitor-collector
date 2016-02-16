package com.baidu.vmonitor.log.stargate.collect;

import com.baidu.fengchao.stargate.common.Response;

public interface StargateLogInfoCollector {
    
    /**
     * 记录日志
     * @param response 返回对象
     * @param begin 请求时间
     * @param end 响应时间
     */
    void genericLogInfo(Response response, long begin, long end);

}
