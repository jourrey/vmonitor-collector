package javassist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.annotation.CollectorParam;
import com.baidu.vmonitor.log.spring.collector.DoLogCollectorInterface;

public class TotemDoLogCollector implements DoLogCollectorInterface<TotemCallLogInfo, TotemAnswerLogInfo> {

    private static final Logger LOG = LoggerFactory.getLogger(TotemDoLogCollector.class);
    
    public TotemCallLogInfo doCall(CollectorParam collectorParam) {
        TotemCallLogInfo totemCallLogInfo = new TotemCallLogInfo();
        totemCallLogInfo.setToken("1");
        totemCallLogInfo.setTokenGroup("1");
        totemCallLogInfo.setMethodName("嘟教授，这是Totem召唤");
        LOG.info("嘟教授{}", totemCallLogInfo);
        return totemCallLogInfo;
    }

    public TotemAnswerLogInfo doAnswer(CollectorParam collectorParam, TotemCallLogInfo logInfo,
                    Object result) {
        TotemAnswerLogInfo totemAnswerLogInfo = new TotemAnswerLogInfo();
        totemAnswerLogInfo.setToken("1");
        totemAnswerLogInfo.setTokenGroup("1");
        LOG.info("嘟教授{}", totemAnswerLogInfo);
        return totemAnswerLogInfo;
    }

    public TotemAnswerLogInfo doException(CollectorParam collectorParam, TotemCallLogInfo logInfo,
                    Throwable throwable) {
        TotemAnswerLogInfo totemAnswerLogInfo = new TotemAnswerLogInfo();
        totemAnswerLogInfo.setToken("1");
        totemAnswerLogInfo.setTokenGroup("1");
        totemAnswerLogInfo.setExceptionMessage("嘟教授，这是Totem回击");
        LOG.info("嘟教授{}", totemAnswerLogInfo);
        return totemAnswerLogInfo;
    }

}
