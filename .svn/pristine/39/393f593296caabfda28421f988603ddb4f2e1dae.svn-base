package com.dwms.examine.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Table(name = "tb_question_info")
@Data
public class Question {
    
    public static final int QUTYPE_SINGLE = 1;
    public static final int QUTYPE_MULTIPLE = 2;
    public static final int QUTYPE_FILL = 3;
    public static final int QUTYPE_SHORT = 4;
    
    /**
     * 题目ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer quId;

    /**
     * 党支部ID
     */
    private Integer partyId;

    /**
     * 题目标题
     */
    private String title;

    /**
     * 题目类型 1单选题 2多选题 3填空题 4简答题
     */
    private Integer quType;

    /**
     * 题目答案（选择题选项分号分隔 A.XXX;B.XXX;...）
     */
    private String answers;

    /**
     * 正确答案（多选题、多个空的填空题答案分号分隔 A;B;...）
     */
    private String rightKey;

    /**
     * 答案解析
     */
    private String analysis;

    /**
     * 状态 1.有效 0.禁用
     */
    private Integer status;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 简答题回答最大字数限制
     */
    private Integer maxNum;

    // 用于搜索条件中的时间字段
    @Transient
    private String timeField;
    
}