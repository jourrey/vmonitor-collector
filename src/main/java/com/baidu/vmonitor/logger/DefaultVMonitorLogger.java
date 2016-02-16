package com.baidu.vmonitor.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @ClassName: DefaultVMonitorLogger
 * @Description: TODO(默认监控日志记录者)
 * @author liuzheng03
 * @date 2014-4-2 下午3:13:17
 */
public class DefaultVMonitorLogger implements VMonitorLogger {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultVMonitorLogger.class);

    private Logger categoryLogger;
    private String logLevel;

    public Logger getCategoryLogger() {
        return categoryLogger;
    }

    public void setCategoryLogger(Logger categoryLogger) {
        this.categoryLogger = categoryLogger;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */
    public DefaultVMonitorLogger() {
        super();
        initDefaultVMonitorLogger(null, null);
    }

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     * 
     * @param logCategory
     *            you log class name
     * @param logLevel
     *            you log Level
     */
    public DefaultVMonitorLogger(Class<?> logCategory, String logLevel) {
        super();
        initDefaultVMonitorLogger(logCategory, logLevel);
    }

    /**
     * @Title: initDefaultVMonitorLogger
     * @Description: TODO(初始化日志配置)
     * @returnType: void
     * @param logCategory
     * @param logLevel
     */
    protected void initDefaultVMonitorLogger(Class<?> logCategory, String logLevel) {
        if (logCategory == null) {// init category logger
            this.categoryLogger = LOG;
        } else {
            this.categoryLogger = LoggerFactory.getLogger(logCategory);
        }
        logLevel = LogLevelController.getLogLevel(logLevel);
        if (logLevel == null) {
            logLevel = "info"; // use info as default if not provided
        }
        this.logLevel = logLevel;
    }

    /**
     * Performs the actual logging.
     * 
     * @param message
     *            the message to log.
     */
    public void doLog(String message) {
        if (logLevel == null) {
            categoryLogger.info(message);
            return;
        }
        if (DEBUG.equalsIgnoreCase(logLevel)) {
            categoryLogger.debug(message);
        } else if (INFO.equalsIgnoreCase(logLevel)) {
            categoryLogger.info(message);
        } else if (WARN.equalsIgnoreCase(logLevel)) {
            categoryLogger.warn(message);
        } else if (ERROR.equalsIgnoreCase(logLevel)) {
            categoryLogger.error(message);
        } else if (TRACE.equalsIgnoreCase(logLevel)) {
            categoryLogger.trace(message);
        } else {
            throw new IllegalArgumentException("LogLevel [" + logLevel + "] is not supported");
        }
    }

    /**
     * Performs the actual logging.
     * 
     * @param format
     *            the format to use.
     * @param args
     *            the args to log.
     */
    public void doLog(String format, Object... objects) {
        if (logLevel == null) {
            categoryLogger.info(format, objects);
            return;
        }
        if (DEBUG.equalsIgnoreCase(logLevel)) {
            categoryLogger.debug(format, objects);
        } else if (INFO.equalsIgnoreCase(logLevel)) {
            categoryLogger.info(format, objects);
        } else if (WARN.equalsIgnoreCase(logLevel)) {
            categoryLogger.warn(format, objects);
        } else if (ERROR.equalsIgnoreCase(logLevel)) {
            categoryLogger.error(format, objects);
        } else if (TRACE.equalsIgnoreCase(logLevel)) {
            categoryLogger.trace(format, objects);
        } else {
            throw new IllegalArgumentException("LogLevel [" + logLevel + "] is not supported");
        }
    }

}
