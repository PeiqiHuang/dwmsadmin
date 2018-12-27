package com.dwms.examine.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

@Table(name = "tb_examine_info")
public class Exam {
    /**
     * 考试进行情况
     */
    public static final int PROGRESS_NOT_BEGIN = 0;
    public static final int PROGRESS_BEGIN = 1;
    public static final int PROGRESS_END = 2;
    
    /**
     * 状态
     */
    public static final int STATUS_DELETE = -9;
    public static final int STATUS_CANCEL = -1;
    public static final int STATUS_WAIT = 0;
    public static final int STATUS_VALID = 1;
    
    /**
     * 试卷ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer examId;

    /**
     * 党支部ID
     */
    private Integer partyId;

    /**
     * 考试名称
     */
    private String examName;

    /**
     * 考试说明
     */
    private String examDesc;

    /**
     * 总分
     */
    private Integer totalScore;

    /**
     * 及格分
     */
    private Integer passScore;

    /**
     * 总题数(实际添加题目数更新)
     */
    private Integer quNum;

    /**
     * 考试时长（分钟）
     */
    private Integer timeLength;

    /**
     * 显示答案时间 -1不显示 0立即 1.自定义时间
     */
    private Integer showType;
    
    /**
     * 显示答案时间（showType 1 设置）
     */
    private Date showTime;

    /**
     * 考试消息app推送 1是 0否
     */
    private Integer pushMsg;

    /**
     * 考试结束提醒（固定提前多少分钟） 1是 0否
     */
    private Integer endMsg;

    /**
     * 预览试卷userId
     */
    private String previewer;

    /**
     * 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    private Integer status;

    /**
     * 考试开始时间
     */
    private Date beginTime;

    /**
     * 考试结束时间
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

    // 用于搜索条件中的时间字段
    @Transient
    @Getter
    @Setter
    private String timeField;
    
    // 考试进行状态
    @Transient
    @Getter
    @Setter
    private Integer progress;
    
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
     * 获取党支部ID
     *
     * @return partyId - 党支部ID
     */
    public Integer getPartyId() {
        return partyId;
    }

    /**
     * 设置党支部ID
     *
     * @param partyId 党支部ID
     */
    public void setPartyId(Integer partyId) {
        this.partyId = partyId;
    }

    /**
     * 获取考试名称
     *
     * @return examName - 考试名称
     */
    public String getExamName() {
        return examName;
    }

    /**
     * 设置考试名称
     *
     * @param examName 考试名称
     */
    public void setExamName(String examName) {
        this.examName = examName == null ? null : examName.trim();
    }

    /**
     * 获取考试说明
     *
     * @return examDesc - 考试说明
     */
    public String getExamDesc() {
        return examDesc;
    }

    /**
     * 设置考试说明
     *
     * @param examDesc 考试说明
     */
    public void setExamDesc(String examDesc) {
        this.examDesc = examDesc == null ? null : examDesc.trim();
    }

    /**
     * 获取总分
     *
     * @return totalScore - 总分
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * 设置总分
     *
     * @param totalScore 总分
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 获取及格分
     *
     * @return passScore - 及格分
     */
    public Integer getPassScore() {
        return passScore;
    }

    /**
     * 设置及格分
     *
     * @param passScore 及格分
     */
    public void setPassScore(Integer passScore) {
        this.passScore = passScore;
    }

    /**
     * 获取总题数(实际添加题目数更新)
     *
     * @return quNum - 总题数(实际添加题目数更新)
     */
    public Integer getQuNum() {
        return quNum;
    }

    /**
     * 设置总题数(实际添加题目数更新)
     *
     * @param quNum 总题数(实际添加题目数更新)
     */
    public void setQuNum(Integer quNum) {
        this.quNum = quNum;
    }

    /**
     * 获取考试时长（分钟）
     *
     * @return timeLength - 考试时长（分钟）
     */
    public Integer getTimeLength() {
        return timeLength;
    }

    /**
     * 设置考试时长（分钟）
     *
     * @param timeLength 考试时长（分钟）
     */
    public void setTimeLength(Integer timeLength) {
        this.timeLength = timeLength;
    }

    /**
     * 获取显示答案时间 -1不显示 0立即 1.自定义时间
     *
     * @return showType - 显示答案时间 -1不显示 0立即 1.自定义时间
     */
    public Integer getShowType() {
        return showType;
    }

    /**
     * 设置显示答案时间 -1不显示 0立即 1.自定义时间
     *
     * @param showType 显示答案时间 -1不显示 0立即 1.自定义时间
     */
    public void setShowType(Integer showType) {
        this.showType = showType;
    }

    /**
     * 获取考试消息app推送 1是 0否
     *
     * @return pushMsg - 考试消息app推送 1是 0否
     */
    public Integer getPushMsg() {
        return pushMsg;
    }

    /**
     * 设置考试消息app推送 1是 0否
     *
     * @param pushMsg 考试消息app推送 1是 0否
     */
    public void setPushMsg(Integer pushMsg) {
        this.pushMsg = pushMsg;
    }

    /**
     * 获取考试结束提醒（固定提前多少分钟） 1是 0否
     *
     * @return endMsg - 考试结束提醒（固定提前多少分钟） 1是 0否
     */
    public Integer getEndMsg() {
        return endMsg;
    }

    /**
     * 设置考试结束提醒（固定提前多少分钟） 1是 0否
     *
     * @param endMsg 考试结束提醒（固定提前多少分钟） 1是 0否
     */
    public void setEndMsg(Integer endMsg) {
        this.endMsg = endMsg;
    }

    /**
     * 获取预览试卷userId
     *
     * @return previewer - 预览试卷userId
     */
    public String getPreviewer() {
        return previewer;
    }

    /**
     * 设置预览试卷userId
     *
     * @param previewer 预览试卷userId
     */
    public void setPreviewer(String previewer) {
        this.previewer = previewer == null ? null : previewer.trim();
    }

    /**
     * 获取状态 -9已删除 -1已取消 0待发布 1已发布
     *
     * @return status - 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 -9已删除 -1已取消 0待发布 1已发布
     *
     * @param status 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取考试开始时间
     *
     * @return beginTime - 考试开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置考试开始时间
     *
     * @param beginTime 考试开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取考试结束时间
     *
     * @return endTime - 考试结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置考试结束时间
     *
     * @param endTime 考试结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取发布时间
     *
     * @return releaseTime - 发布时间
     */
    public Date getReleaseTime() {
        return releaseTime;
    }

    /**
     * 设置发布时间
     *
     * @param releaseTime 发布时间
     */
    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
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

    @Override
    public String toString() {
        return "Examine [examId=" + examId + ", partyId=" + partyId
                + ", examName=" + examName + ", examDesc=" + examDesc
                + ", totalScore=" + totalScore + ", passScore=" + passScore
                + ", quNum=" + quNum + ", timeLength=" + timeLength
                + ", showType=" + showType + ", pushMsg=" + pushMsg
                + ", endMsg=" + endMsg + ", previewer=" + previewer
                + ", status=" + status + ", beginTime=" + beginTime
                + ", endTime=" + endTime + ", releaseTime=" + releaseTime
                + ", createTime=" + createTime + "]";
    }

    public Date getShowTime() {
        return showTime;
    }

    public void setShowTime(Date showTime) {
        this.showTime = showTime;
    }
    
}