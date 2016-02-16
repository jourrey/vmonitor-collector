package com.baidu.vmonitor.log.filter.collector;

import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.collector.CollectorInterface;
import com.baidu.vmonitor.log.filter.model.FilterAnswerLogInfo;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;

/**
 * @ClassName: FilterCollectorInterface
 * @Description: TODO(Filter日志收集者接口)
 * @author liuzheng03
 * @date 2014-4-2 下午2:59:24
 * 
 * @param <C>
 * @param <A>
 */
public interface FilterCollectorInterface<C extends FilterCallLogInfo, A extends FilterAnswerLogInfo> extends CollectorInterface {

    /**
     * @Title: doCall
     * @Description: TODO(生成调用日志)
     * @returnType: C
     * @param collectorParam
     *            collector的辅助参数
     * @return
     */
    C doCall(CollectorParam collectorParam);

    /**
     * @Title: doAnswer
     * @Description: TODO(生成响应日志)
     * @returnType: A
     * @param collectorParam
     *            collector的辅助参数
     * @param logInfo
     *            doCall返回的LogInfo类或者LogInfo子类的实例
     * @param result
     *            方法返回值
     * @return
     */
    A doAnswer(CollectorParam collectorParam, C logInfo, Object result);

    /**
     * @Title: doException
     * @Description: TODO(生成异常日志)
     * @returnType: A
     * @param collectorParam
     *            collector的辅助参数
     * @param logInfo
     *            doCall返回的LogInfo类或者LogInfo子类的实例
     * @param throwable
     *            处理过程中出现的异常
     * @return
     */
    A doException(CollectorParam collectorParam, C logInfo, Throwable throwable);

}
