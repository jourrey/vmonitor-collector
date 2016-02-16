package com.baidu.vmonitor.collect;

import com.baidu.vmonitor.model.LogInfo;

/**
 * 
 * @author liuzheng03
 *
 * @param <L>
 */
public interface CollectInterface<L extends LogInfo> {

    /**
     * 生成记录日志
     * 
     * @param info
     *            记录对象
     */
    void doCollect(L... info);

}
