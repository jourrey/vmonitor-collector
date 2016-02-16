package javassist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.log.filter.collector.FilterCollectorInterface;
import com.baidu.vmonitor.log.filter.model.FilterAnswerLogInfo;
import com.baidu.vmonitor.log.filter.model.FilterCallLogInfo;

public class TotemFilterCollector implements FilterCollectorInterface<FilterCallLogInfo, FilterAnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(TotemFilterCollector.class);
    
    public FilterCallLogInfo doCall(CollectorParam collectorParam) {
        FilterCallLogInfo totemCallLogInfo = new FilterCallLogInfo();
        totemCallLogInfo.setToken("1");
        totemCallLogInfo.setTokenGroup("1");
        totemCallLogInfo.setServiceName("嘟教授，这是Totem召唤");
        LOG.info("嘟教授{}", totemCallLogInfo);
        return totemCallLogInfo;
    }

    @Override
    public FilterAnswerLogInfo doAnswer(CollectorParam collectorParam, FilterCallLogInfo logInfo, Object result) {
        FilterAnswerLogInfo totemAnswerLogInfo = new FilterAnswerLogInfo();
        totemAnswerLogInfo.setToken("1");
        totemAnswerLogInfo.setTokenGroup("1");
        LOG.info("嘟教授{}", totemAnswerLogInfo);
        return totemAnswerLogInfo;
    }

    @Override
    public FilterAnswerLogInfo doException(CollectorParam collectorParam, FilterCallLogInfo logInfo, Throwable throwable) {
        FilterAnswerLogInfo totemAnswerLogInfo = new FilterAnswerLogInfo();
        totemAnswerLogInfo.setToken("1");
        totemAnswerLogInfo.setTokenGroup("1");
        totemAnswerLogInfo.setExceptionMessage("嘟教授，这是Totem回击");
        LOG.info("嘟教授{}", totemAnswerLogInfo);
        return totemAnswerLogInfo;
    }

}
