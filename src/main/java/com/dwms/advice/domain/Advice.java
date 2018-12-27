package com.dwms.advice.domain;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Table(name = "tb_advice_info")
@Data
public class Advice {
    /**
     * 反馈建议编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer adviceId;

    /**
     * 反馈人
     */
    private String userId;
    
    @Transient
    private String userName;
    
    @Transient
    private String partyName;

    /**
     * 反馈内容
     */
    private String adviceText;

    /**
     * 反馈时间
     */
    private Date createTime;
    
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