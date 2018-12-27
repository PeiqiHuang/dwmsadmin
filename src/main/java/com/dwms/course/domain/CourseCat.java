package com.dwms.course.domain;

import javax.persistence.*;

@Table(name = "tb_course_category")
public class CourseCat {
    /**
     * 状态
     */
    public static final int STATUS_INVALID = 0;
    public static final int STATUS_VALID = 1;
    
    /**
     * 课程分类ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 状态 1.有效 0.禁用
     */
    private Integer status;

    /**
     * 权重 大的排在前面
     */
    private Integer weight;

    /**
     * 党支部ID
     */
    private Integer partyId;
    
    public Integer getPartyId() {
        return partyId;
    }

    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     * 获取课程分类ID
     *
     * @return categoryId - 课程分类ID
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     * 设置课程分类ID
     *
     * @param categoryId 课程分类ID
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取分类名称
     *
     * @return categoryName - 分类名称
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 设置分类名称
     *
     * @param categoryName 分类名称
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    /**
     * 获取状态 1.有效 0.禁用
     *
     * @return status - 状态 1.有效 0.禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1.有效 0.禁用
     *
     * @param status 状态 1.有效 0.禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取权重 大的排在前面
     *
     * @return weight - 权重 大的排在前面
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 设置权重 大的排在前面
     *
     * @param weight 权重 大的排在前面
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}