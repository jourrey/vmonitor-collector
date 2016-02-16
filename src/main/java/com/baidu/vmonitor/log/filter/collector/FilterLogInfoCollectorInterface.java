package com.baidu.vmonitor.log.filter.collector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baidu.vmonitor.collector.LogInfoCollector;
import com.baidu.vmonitor.log.filter.model.FilterAnswerLogInfo;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;

/**
 * @deprecated
 * @ClassName: FilterLogInfoCollectorInterface 
 * @Description: TODO(Filter日志收集者接口, {@link FilterCollectorInterface}) 
 * @author liuzheng03 
 * @date 2014-4-28 上午11:02:41 
 * 
 * @param <C>
 * @param <A>
 */
public interface FilterLogInfoCollectorInterface<C extends FilterCallLogInfo, A extends FilterAnswerLogInfo> extends
                LogInfoCollector<C, A, HttpServletRequest, HttpServletResponse> {

    /**
     * 生成调用日志
     * 
     * @param request
     *            HttpServletRequest请求
     * @return
     */
    C doCall(HttpServletRequest request);

    /**
     * 生成响应日志
     * 
     * @param logInfo
     *            doCall返回的LogInfo类或者LogInfo子类的实例
     * @param response
     *            HttpServletResponse响应
     * @param throwable
     *            处理过程中出现的异常
     * @return
     */
    A doAnswer(C logInfo, HttpServletResponse response, Throwable throwable);

}
