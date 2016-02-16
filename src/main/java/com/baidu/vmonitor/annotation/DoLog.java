package com.baidu.vmonitor.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.baidu.vmonitor.collector.CollectorInterface;

/**
 * @ClassName: DoLog
 * @Description: TODO(日志注解，被标注的类或者方法将执行日志记录)
 * @author liuzheng03
 * @date 2014-4-2 下午2:56:55
 */
@Documented
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DoLog {

    /**
     * @Title: key
     * @Description: TODO(日志信息的扩展key，默认是无扩展信息，{@link LogInfoKey})
     * @return String[] 返回类型
     * @throws
     */
    public String[] key() default { "NONE" };

    /**
     * @Title: collectorClass 
     * @Description: TODO(日志收集者的class对象) 
     * @returnType: Class<? extends CollectorInterface>
     * @return
     */
    public Class<? extends CollectorInterface> collectorClass() default CollectorInterface.class;

    /**
     * @Title: behaviorType
     * @Description: TODO(注解的行为类型，默认是记录日志，{@link BehaviorType})
     * @returnType: BehaviorType
     * @return
     */
    public BehaviorType behaviorType() default BehaviorType.DO_LOG;

    /**
     * @Title: value
     * @Description: TODO(是否启用注解,默认是true启用注解)
     * @return boolean 返回类型
     * @throws
     */
    public boolean value() default true;

}
