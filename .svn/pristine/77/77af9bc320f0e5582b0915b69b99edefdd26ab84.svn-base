package com.dwms.due.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Table(name = "tb_due_info")
@Data
public class Due {
    /**
     * 状态
     */
    public static final int STATUS_DELETE = -9;
    public static final int STATUS_CANCEL = -1;
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_VALID = 1;
    
    /**
     * 缴费项目
     */
    public static final int ITEM_NORMAL = 1;
    public static final int ITEM_SPECIAL = 2;
    
    /**
     * 缴费类型
     */
    public static final int TYPE_FIX = 1;
    public static final int TYPE_SPECIAL = 2;
    
    /**
     * 缴费ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dueId;

    /**
     * 党支部id
     */
    private Integer partyId;

    /**
     * 缴费标题
     */
    private String title;

    /**
     * 缴费描述
     */
    private String dueDesc;

    /**
     * 缴费项目 1党费 2特殊党费
     */
    private Integer dueItem;

    /**
     * 1固定金额 2自由金额
     */
    private Integer dueType;

    /**
     * 缴费金额
     */
    private Integer due;

    /**
     * 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    private Integer status;

    /**
     * 截止日期
     */
    private Date endTime;

    /**
     * 发布时间
     */
    private Date releaseTime;

    /**
     * 创建时间
     */
    private Date createTime;
    
    private Integer accId;
    
    // 用于搜索条件中的时间字段
    @Transient
    private String timeField;
}