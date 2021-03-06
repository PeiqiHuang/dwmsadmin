package com.dwms.course.domain;

import java.util.Date;

import javax.persistence.*;

import com.dwms.common.annotation.ExportConfig;

@Table(name = "tb_course_user")
public class CourseUser {
    
    /**
     * 状态
     */
    public static final int STATUS_INVALID = -1;
    public static final int STATUS_VALID = 0;
    public static final int STATUS_FINISH = 1;
    /**
     * 逻辑ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cuId;

    /**
     * 课程ID
     */
    private Integer courseId;

    /**
     * 课程参与党员
     */
    private String userId;

    @Transient
    @ExportConfig(value = "学习党员")
    private String userName;
    
    /**
     * 学习时长（分钟）
     */
    @ExportConfig(value = "学习时长（分钟）")
    private Integer timeLength;

    /**
     * 状态 -1未生效 0已生效（未完成） 1已完成
     */
    @ExportConfig(value = "状态", convert = "s:-1=未生效,1=已完成,0=未生效")
    private Integer status;

    /**
     * 完成时间
     */
    @ExportConfig(value = "完成时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date finishTime;

    /**
     * 创建时间
     */
    @ExportConfig(value = "创建时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date createTime;

    /**
     * 进度，整数，100表示100%
     */
    private Integer progress;
    
    /**
     * 剩余课时
     */
    private Integer restChapter;
    
    /**
     * 获取逻辑ID
     *
     * @return cuId - 逻辑ID
     */
    public Integer getCuId() {
        return cuId;
    }

    /**
     * 设置逻辑ID
     *
     * @param cuId 逻辑ID
     */
    public void setCuId(Integer cuId) {
        this.cuId = cuId;
    }

    /**
     * 获取课程ID
     *
     * @return courseId - 课程ID
     */
    public Integer getCourseId() {
        return courseId;
    }

    /**
     * 设置课程ID
     *
     * @param courseId 课程ID
     */
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    /**
     * 获取课程参与党员
     *
     * @return userId - 课程参与党员
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置课程参与党员
     *
     * @param userId 课程参与党员
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取学习时长（分钟）
     *
     * @return timeLength - 学习时长（分钟）
     */
    public Integer getTimeLength() {
        return timeLength;
    }

    /**
     * 设置学习时长（分钟）
     *
     * @param timeLength 学习时长（分钟）
     */
    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    /**
     * 获取状态 -1未生效 0已生效（未完成） 1已完成
     *
     * @return status - 状态 -1未生效 0已生效（未完成） 1已完成
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 -1未生效 0已生效（未完成） 1已完成
     *
     * @param status 状态 -1未生效 0已生效（未完成） 1已完成
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取完成时间
     *
     * @return finishTime - 完成时间
     */
    public Date getFinishTime() {
        return finishTime;
    }

    /**
     * 设置完成时间
     *
     * @param finishTime 完成时间
     */
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public Integer getRestChapter() {
        return restChapter;
    }

    public void setRestChapter(Integer restChapter) {
        this.restChapter = restChapter;
    }
    
}