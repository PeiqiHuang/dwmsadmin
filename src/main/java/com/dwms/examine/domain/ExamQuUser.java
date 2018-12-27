package com.dwms.examine.domain;

import javax.persistence.*;

@Table(name = "tb_examine_question_user")
public class ExamQuUser {
    /**
     * 状态
     */
    public static final int STATUS_NOT_JUDGE = 0;
    public static final int STATUS_JUDGE = 1;
    
    /**
     * 题目人员ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer equId;

    /**
     * 考试题目ID
     */
    private Integer eqId;

    /**
     * 考试人员Id
     */
    private Integer euId;

    /**
     * 题目得分
     */
    private Integer score;

    /**
     * 回答答案（多选题、多个空的填空题答案分号分隔 A;B;...）
     */
    private String answer;

    /**
     * 状态 -1回答错误 0未判分 1已判分/回答正确
     */
    private Integer status;

    /**
     * 获取题目人员ID
     *
     * @return equId - 题目人员ID
     */
    public Integer getEquId() {
        return equId;
    }

    /**
     * 设置题目人员ID
     *
     * @param equId 题目人员ID
     */
    public void setEquId(Integer equId) {
        this.equId = equId;
    }

    /**
     * 获取考试题目ID
     *
     * @return eqId - 考试题目ID
     */
    public Integer getEqId() {
        return eqId;
    }

    /**
     * 设置考试题目ID
     *
     * @param eqId 考试题目ID
     */
    public void setEqId(Integer eqId) {
        this.eqId = eqId;
    }

    /**
     * 获取考试人员Id
     *
     * @return euId - 考试人员Id
     */
    public Integer getEuId() {
        return euId;
    }

    /**
     * 设置考试人员Id
     *
     * @param euId 考试人员Id
     */
    public void setEuId(Integer euId) {
        this.euId = euId;
    }

    /**
     * 获取题目得分
     *
     * @return score - 题目得分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置题目得分
     *
     * @param score 题目得分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取回答答案（多选题、多个空的填空题答案分号分隔 A;B;...）
     *
     * @return answer - 回答答案（多选题、多个空的填空题答案分号分隔 A;B;...）
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 设置回答答案（多选题、多个空的填空题答案分号分隔 A;B;...）
     *
     * @param answer 回答答案（多选题、多个空的填空题答案分号分隔 A;B;...）
     */
    public void setAnswer(String answer) {
        this.answer = answer == null ? null : answer.trim();
    }

    /**
     * 获取状态 0未判分 1已判分
     *
     * @return status - 状态 0未判分 1已判分
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0未判分 1已判分
     *
     * @param status 状态 0未判分 1已判分
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}