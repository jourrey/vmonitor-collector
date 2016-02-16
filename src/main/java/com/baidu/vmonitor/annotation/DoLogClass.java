package com.baidu.vmonitor.annotation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.vmonitor.collector.CollectorInterface;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfig;
import com.baidu.vmonitor.common.config.VmonitorCollectorConfigKey;

/**
 * @ClassName: DoLogClass 
 * @Description: TODO(注解DoLog的描述类，满足不可使用DoLog注解的场景，保持与DoLog一致，{@link DoLog}) 
 * @author liuzheng03 
 * @date 2014-4-29 下午12:51:49 
 */
public class DoLogClass {
    
    private static final Logger LOG = LoggerFactory.getLogger(DoLogClass.class);

    private final String[] key;

    private final Class<? extends CollectorInterface> collectorClass;

    private final BehaviorType behaviorType;

    private final boolean value;

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param key
     * @param collectorClass
     * @param behaviorType
     * @param value
     */
    public DoLogClass(String[] key, Class<? extends CollectorInterface> collectorClass, BehaviorType behaviorType,
                    boolean value) {
        super();
        this.key = key;
        this.collectorClass = collectorClass;
        this.behaviorType = null == behaviorType ? BehaviorType.DO_LOG : behaviorType;
        this.value = value;
    }

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param key
     * @param collectorClass
     * @param behaviorType
     * @param value
     */
    public DoLogClass(String key, Class<? extends CollectorInterface> collectorClass, String behaviorType, String value) {
        super();
        BehaviorType bType;
        try {
            bType = BehaviorType.valueOf(behaviorType);
        } catch (Exception e) {
            LOG.warn("Custom behaviorType.{} failure default use BehaviorType.DO_LOG", behaviorType);
            bType = BehaviorType.DO_LOG;
        }
        this.key = null == key ? null : key.split(VmonitorCollectorConfig.getInstance().getString(
                        VmonitorCollectorConfigKey.LOG_INFO_SPLIT));
        this.collectorClass = collectorClass;
        this.behaviorType = bType;
        this.value = null == value ? true : Boolean.parseBoolean(value);
    }

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param doLog
     */
    public DoLogClass(DoLog doLog) {
        super();
        this.key = doLog.key();
        this.collectorClass = doLog.collectorClass();
        this.behaviorType = doLog.behaviorType();
        this.value = doLog.value();
    }

    public String[] getKey() {
        return key;
    }

    public Class<? extends CollectorInterface> getCollectorClass() {
        return collectorClass;
    }

    public BehaviorType getBehaviorType() {
        return behaviorType;
    }

    public boolean isValue() {
        return value;
    }

}
