package com.dwms.common.util;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.Properties;

/**
 * yaml工具解析类
 * @author windy
 * @date 2018-11-21
 */
public class YamlUtils {

    /**
     * map结构
     * @author windy
     * @date 2018-11-21
     */
    public static Map<String, Object> map(String yamlSource) {
        try {
            YamlMapFactoryBean yaml = new YamlMapFactoryBean();
            yaml.setResources(new ClassPathResource(yamlSource));
            return yaml.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * properties结构
     * @author windy
     * @date 2018-11-21
     */
    public static Properties properties(String yamlSource) {
        try {
            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ClassPathResource(yamlSource));
            return yaml.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询值
     * @author windy
     * @date 2018-11-21
     */
    public static String get(String yamlSource, String key) {
        try {
            if (key == null || "".equals(key)) {
                return null;
            }

            YamlPropertiesFactoryBean yaml = new YamlPropertiesFactoryBean();
            yaml.setResources(new ClassPathResource(yamlSource));
            Properties prop = yaml.getObject();
            if (prop != null) {
                return prop.getProperty(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        System.out.println(" ------------------------- map -------------------------");
        Map<String, Object> map = map("application.yml");
        System.out.println(JSON.toJSONString(map));
        for (String s : map.keySet()) {
            System.out.println(s);
        }

        System.out.println(" ------------------------- properties -------------------------");
        Properties properties = properties("application.yml");
        System.out.println(JSON.toJSONString(properties));
        for (Object o : properties.keySet()) {
            System.out.println(o);
        }

        System.out.println(" ------------------------- get -------------------------");
        String value = get("application.yml", "spring.profiles.active");
        System.out.println(value);

    }
}