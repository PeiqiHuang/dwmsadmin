package com.dwms.examine.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.ToString;

@Table(name = "tb_examine_question")
@ToString
public class ExamQu {
    /**
     * 考试题目ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eqId;

    /**
     * 试卷ID
     */
    private Integer examId;

    /**
     * 题目ID
     */
    private Integer quId;

    /**
     * 题目序号 1 2 3...
     */
    private Integer quNo;

    /**
     * 题目分数
     */
    private Integer score;

    /**
     * 创建时间
     */
    private Date createTime;

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
     * 获取试卷ID
     *
     * @return examId - 试卷ID
     */
    public Integer getExamId() {
        return examId;
    }

    /**
     * 设置试卷ID
     *
     * @param examId 试卷ID
     */
    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    /**
     * 获取题目ID
     *
     * @return quId - 题目ID
     */
    public Integer getQuId() {
        return quId;
    }

    /**
     * 设置题目ID
     *
     * @param quId 题目ID
     */
    public void setQuId(Integer quId) {
        this.quId = quId;
    }

    /**
     * 获取题目序号 1 2 3...
     *
     * @return quNo - 题目序号 1 2 3...
     */
    public Integer getQuNo() {
        return quNo;
    }

    /**
     * 设置题目序号 1 2 3...
     *
     * @param quNo 题目序号 1 2 3...
     */
    public void setQuNo(Integer quNo) {
        this.quNo = quNo;
    }

    /**
     * 获取题目分数
     *
     * @return score - 题目分数
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置题目分数
     *
     * @param score 题目分数
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取创建时间
     *
     * @return createTime - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}