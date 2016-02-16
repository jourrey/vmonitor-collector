package com.baidu.vmonitor.log.filter.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.BehaviorType;
import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.annotation.DoLogClass;
import com.baidu.vmonitor.collector.CollectorInterface;
import com.baidu.vmonitor.common.MethodRepository;
import com.baidu.vmonitor.common.VmonitorContext;
import com.baidu.vmonitor.log.filter.collect.FilterCollect;
import com.baidu.vmonitor.log.filter.model.FilterAnswerLogInfo;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;
import com.baidu.vmonitor.model.LogInfo;

/**
 * @ClassName: FilterCollectorDecorator 
 * @Description: TODO(日志收集者装饰器) 
 * @author liuzheng03 
 * @date 2014-4-28 下午2:02:07 
 */
public class FilterCollectorDecorator {

    private static final Logger LOG = LoggerFactory.getLogger(FilterCollectorDecorator.class);

    private static FilterCollectorDecorator instance = null;
    private FilterCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> filterCollectorInterface;

    private FilterCollectorDecorator() {
        filterCollectorInterface = new FilterCollector();
    }

    private static synchronized void syncInit() {
        if (instance == null) {
            instance = new FilterCollectorDecorator();
        }
    }

    public static FilterCollectorDecorator getInstance() {
        if (instance == null) {
            syncInit();
        }
        return instance;
    }

    /**
     * @Title: readResolve
     * @Description: TODO(如果该对象被用于序列化，可以保证对象在序列化前后保持一致)
     * @returnType: Object
     * @return
     */
    public Object readResolve() {
        return getInstance();
    }

    /**
     * @Title: getFilterCollector
     * @Description: TODO(获取日志收集器)
     * @returnType: 
     *              FilterCollectorInterface<FilterCallLogInfo,FilterAnswerLogInfo
     *              >
     * @param collectorClass
     * @return
     */
    public FilterCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> getFilterCollector(
                    Class<? extends CollectorInterface> collectorClass) {
        if (null != collectorClass) {
            try {
                @SuppressWarnings("unchecked")
                FilterCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> collector = (FilterCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo>) MethodRepository
                                .getInstance().getCollector(collectorClass);
                if (null != collector) {
                    LOG.debug("FilterCollector {}", collectorClass);
                    return collector;
                }
            } catch (Throwable e) {
                LOG.error("getFilterCollector failure", e);
            }
        }
        return filterCollectorInterface;
    }

    /**
     * @Title: doCollect
     * @Description: TODO(记录日志)
     * @returnType: void
     * @param doLog
     * @param info
     */
    private void doCollect(DoLogClass doLogClass, LogInfo... info) {
        try {
            if (BehaviorType.DO_LOG.equals(doLogClass.getBehaviorType())) {
                FilterCollect.getInstance().doCollect(info);
            }
        } catch (Throwable e) {
            LOG.error("doCollect failure", e);
        }
    }

    public FilterCallLogInfo doCall(CollectorParam collectorParam) {
        try {
            FilterCallLogInfo callLogInfo = getFilterCollector(collectorParam.getDoLogClass().getCollectorClass())
                            .doCall(collectorParam);
            callLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());// 禁止修改
            doCollect(collectorParam.getDoLogClass(), callLogInfo);
            return callLogInfo;
        } catch (Exception e) {
            LOG.error("doCollect failure", e);
            return null;
        }

    }

    public FilterAnswerLogInfo doAnswer(CollectorParam collectorParam, FilterCallLogInfo logInfo, Object result) {
        try {
            FilterAnswerLogInfo answerLogInfo = getFilterCollector(collectorParam.getDoLogClass().getCollectorClass())
                            .doAnswer(collectorParam, logInfo, result);
            answerLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());// 禁止修改
            doCollect(collectorParam.getDoLogClass(), answerLogInfo);
            return answerLogInfo;
        } catch (Exception e) {
            LOG.error("doCollect failure", e);
            return null;
        }
    }

    public FilterAnswerLogInfo doException(CollectorParam collectorParam, FilterCallLogInfo logInfo, Throwable throwable) {
        try {
            FilterAnswerLogInfo answerLogInfo = getFilterCollector(collectorParam.getDoLogClass().getCollectorClass())
                            .doAnswer(collectorParam, logInfo, throwable);
            answerLogInfo.setTokenGroup(VmonitorContext.getTokenGroup());// 禁止修改
            doCollect(collectorParam.getDoLogClass(), answerLogInfo);
            return answerLogInfo;
        } catch (Exception e) {
            LOG.error("doCollect failure", e);
            return null;
        }
    }

}
