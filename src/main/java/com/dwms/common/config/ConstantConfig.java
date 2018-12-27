package com.dwms.common.config;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import com.dwms.common.util.YamlFactory;

/**
 * 图片配置
 * @author windy
 * @date 2016-12-15
 */
@Data
@Configuration
@PropertySources(@PropertySource(value = "classpath:env/${spring.profiles.active}/sys.yml", factory = YamlFactory.class))
public class ConstantConfig {

	@Value("${user.password}")
    private String userDefaultPassword;
	
	@Value("${admin.roleId}")
	private String adminRoleId;
	
	@Value("${course.readSpeed}")
	private int readSpeed;

}