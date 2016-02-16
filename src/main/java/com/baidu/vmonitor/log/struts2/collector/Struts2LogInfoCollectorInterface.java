package com.baidu.vmonitor.log.struts2.collector;

import com.baidu.vmonitor.collector.LogInfoCollector;
import com.baidu.vmonitor.log.struts2.model.Struts2AnswerLogInfo;
import com.baidu.vmonitor.log.struts2.model.Struts2CallLogInfo;
import com.opensymphony.xwork2.ActionInvocation;

public interface Struts2LogInfoCollectorInterface<C extends Struts2CallLogInfo, A extends Struts2AnswerLogInfo> extends LogInfoCollector<C, A, ActionInvocation, ActionInvocation> {

    /**
     * 生成调用日志
     * 
     * @param invocation struts2 interceptor实现的参数
     * @return
     */
    C doCall(ActionInvocation invocation);

    /**
     * 生成响应日志
     * 
     * @param logInfo doCall返回的LogInfo类或者LogInfo子类的实例
     * @param invocation struts2 interceptor实现的参数
     * @param throwable 处理过程中出现的异常
     * @return
     */
    A doAnswer(C logInfo, ActionInvocation invocation, Throwable throwable);
    
}
