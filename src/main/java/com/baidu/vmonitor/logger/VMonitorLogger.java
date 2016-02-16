package com.baidu.vmonitor.logger;

/**
 * @ClassName: VMonitorLogger
 * @Description: TODO(监控日志记录者接口)
 * @author liuzheng03
 * @date 2014-4-2 下午3:12:28
 */
public interface VMonitorLogger {

    public static final String TRACE = "trace";
    public static final String INFO = "info";
    public static final String DEBUG = "debug";
    public static final String WARN = "warn";
    public static final String ERROR = "error";

    /**
     * @Title: doLog
     * @Description: TODO(记录日志)
     * @returnType: void
     * @param message
     *            the message to log.
     */
    void doLog(String message);

    /**
     * @Title: doLog
     * @Description: TODO(记录日志)
     * @returnType: void
     * @param format
     *            the format to use.
     * @param objects
     *            the args to log.
     */
    void doLog(String format, Object... objects);

}
