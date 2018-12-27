package com.dwms.advert.domain;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Table(name = "tb_advert_info")
@Data
public class Advert {
    /**
     * 状态
     */
    public static final int STATUS_VALID = 1;
    public static final int STATUS_LOCK = 0;
    
    /**
     * 广告id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adId;

    /**
     * 广告名称
     */
    private String name;

    /**
     * 动作
     */
    private String action;

    /**
     * 简介
     */
    private String summary;

    /**
     * 权重，0-10，默认0，越大表示越前
     */
    private Integer weight;

    /**
     * 广告位置key
     */
    private String adKey;
    
    @Transient
    private String keyName;

    /**
     * 广告开始时间
     */
    private Date beginTime;

    /**
     * 广告结束时间
     */
    private Date endTime;

    /**
     * 状态 0禁用 1启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
    
    /**
     * 广告封面
     */
    private String cover;
    
    @Transient
    private MultipartFile coverImg;
    
    /**
     * 党支部ID
     */
    private Integer partyId;
    
    // 用于搜索条件中的时间字段
    @Transient
    private String timeField;
    @Transient
    private String beginField;
    @Transient
    private String endField;
    
    public String getBeginField() {
        if (StringUtils.isNotBlank(timeField)) {
            String[] timeArr = timeField.split("~");
            return timeArr[0];
        }
        return beginField;
    }

    public String getEndField() {
        if (StringUtils.isNotBlank(timeField)) {
            String[] timeArr = timeField.split("~");
            return timeArr[1];
        }
        return endField;
    }
}