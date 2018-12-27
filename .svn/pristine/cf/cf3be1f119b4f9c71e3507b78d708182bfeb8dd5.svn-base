package com.dwms.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring工具类
 * @author windy
 * @date 2018-11-21
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Web容器
     * @author windy
     * @date 2018-11-21
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtils.applicationContext == null) {
            SpringUtils.applicationContext = applicationContext;
        }
    }

    /**
     * 获取Spring管理的Bean不包括Spring mvc管理的Bean
     * @author windy
     * @date 2018-11-21
     */
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * 获取Spring管理的Bean不包括Spring mvc管理的Bean
     * @author windy
     * @date 2018-11-21
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

}
