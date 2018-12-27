package com.dwms.help.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.Data;

@Table(name = "tb_help_type")
@Data
public class HelpType {
    /**
     * 状态
     */
    public static final int STATUS_VALID = 1;
    public static final int STATUS_LOCK = 0;
    
    /**
     * 类型编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer typeId;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 类型状态：1正常 0禁用
     */
    private Integer typeStatus;

    /**
     * 创建时间
     */
    private Date createTime;

}