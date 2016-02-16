package com.baidu.vmonitor.collector;

/**
 * @deprecated
 * @ClassName: LogInfoCollector 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author liuzheng03 
 * @date 2014-4-28 下午1:28:06 
 * 
 * @param <C>
 * @param <A>
 * @param <CP>
 * @param <AP>
 */
public interface LogInfoCollector<C, A, CP, AP> {

    /**
     * 生成调用日志
     * 
     * @param info
     * @return
     */
    C doCall(CP info);

    /**
     * 生成响应日志
     * 
     * @param logInfo
     * @param info
     * @param throwable
     * @return
     */
    A doAnswer(C logInfo, AP info, Throwable throwable);

}
