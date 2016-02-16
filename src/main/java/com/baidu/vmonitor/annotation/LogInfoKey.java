package com.baidu.vmonitor.annotation;

/**
 * @ClassName: LogInfoKey
 * @Description: TODO(默认支持的扩展日志信息)
 * @author liuzheng03
 * @date 2014-4-2 下午2:54:31
 */
public enum LogInfoKey {

    /* 增加调用参数 */
    PARAMETERS,

    /* 处理耗时 */
    CONSUMING_TIME,

    /* 增加返回结果 */
    RESULT,

    /* 增加执行异常 */
    EXCEPTION,

    /* 默认值 */
    NONE

}
