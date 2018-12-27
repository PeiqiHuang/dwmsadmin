package com.dwms.help.domain;

import java.util.Date;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;

import lombok.Data;

@Table(name = "tb_help_info")
@Data
public class Help {
    /**
     * 信息编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer infoId;

    /**
     * 帮助类型
     */
    private Integer infoType;
    
    @Transient
    private String typeName;

    /**
     * 信息标题
     */
    private String title;

    /**
     * 信息内容
     */
    private String text;

    /**
     * 信息状态：1正常 0禁用
     */
    private Integer infoStatus;

    /**
     * 创建时间
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