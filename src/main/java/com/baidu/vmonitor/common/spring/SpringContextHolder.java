package com.baidu.vmonitor.common.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    private static final Logger LOG = LoggerFactory.getLogger(SpringContextHolder.class);

    private static ApplicationContext applicationContext = null;

    public void setApplicationContext(ApplicationContext applicationContext) {
        LOG.debug("注入ApplicationContext到SpringContextHolder:{}", applicationContext);

        if (SpringContextHolder.applicationContext != null) {
            LOG.warn("SpringContextHolder中的ApplicationContext被覆盖, 原有ApplicationContext为:{}",
                            SpringContextHolder.applicationContext);
        }

        SpringContextHolder.applicationContext = applicationContext;
    }

    public void destroy() throws Exception {
        SpringContextHolder.clear();
    }

    public static ApplicationContext getApplicationContext() {
        assertContextInjected();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        assertContextInjected();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }

    public static void clear() {
        LOG.debug("清除SpringContextHolder中的ApplicationContext:{}", applicationContext);
        applicationContext = null;
    }

    private static void assertContextInjected() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }

}
