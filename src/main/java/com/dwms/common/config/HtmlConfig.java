package com.dwms.common.config;

import com.dwms.common.util.YamlFactory;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.util.List;

/**
 * 图片配置
 * @author windy
 * @date 2016-12-15
 */
@Data
@Configuration
@PropertySources(@PropertySource(value = "classpath:env/${spring.profiles.active}/sys.yml", factory = YamlFactory.class))
public class HtmlConfig {

    public static final String HTML = ".html";
    
	@Value("${image.uploadpath}")
    private String uploadPath;// 上传路径
    @Value("${image.accesspath}")
    private String accessPath;// 访问路径

    @Value("${html.root}")
    private String root;//根地址
    
    @Value("${html.mtgSummary}")
    private String mtgSummary;//会议纪要
    
    @Value("${html.activity}")
    private String activity;//活动
    
    @Value("${html.notice}")
    private String notice;//通知

}