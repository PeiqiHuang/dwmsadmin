package com.dwms.examine.domain.vo;

import lombok.Data;

/**
 * 答卷详情
 * 
 * 
 * @author PeiqiHuang
 * @date 2018年12月12日 上午9:00:56
 */
@Data
public class ExamQuUserVo {
    
    /* tb_examine_question_user */
    private Integer equId;
    
    private String answer;//回答答案
    
    private Integer gotScore;//题目得分
    
    /* tb_examine_question */
    private Integer quNo;//题目序号
    
    private Integer score;//题目分数
    
    /* tb_question_info */
    private String title;
    
    private Integer quType;
    
    private String answers;
    
    private String rightKey;
}
