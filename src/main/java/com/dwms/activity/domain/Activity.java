package com.dwms.activity.domain;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_activity_info")
@ToString
public class Activity {
    /**
     * 活动进行情况
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
     * 活动id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer actId;

    /**
     * 党支部ID
     */
    private Integer partyId;

    /**
     * 活动名称
     */
    private String actName;

    /**
     * 活动详情
     */
    private String actDesc;

    /**
     * 活动地址
     */
    private String address;

    /**
     * 活动文件地址
     */
    private String filePath;

    // 编辑器内容
    @Transient
    @Getter
    @Setter
    private String content;
    
    /**
     * 状态 -9已删除 -1已取消 0待发布 1已发布
     */
    private Integer status;

    /**
     * 活动开始时间
     */
    private Date beginTime;

    /**
     * 活动结束时间
     */
    private Date endTime;

    /**
     * 报名截止时间
     */
    private Date applyEndTime;

    /**
     * 修改时间
     */
    private Date updateTime;

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
     * 获取活动id
     *
     * @return actId - 活动id
     */
    public Integer getActId() {
        return actId;
    }

    /**
     * 设置活动id
     *
     * @param actId 活动id
     */
    public void setActId(Integer actId) {
        this.actId = actId;
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
     * 获取活动名称
     *
     * @return actName - 活动名称
     */
    public String getActName() {
        return actName;
    }

    /**
     * 设置活动名称
     *
     * @param actName 活动名称
     */
    public void setActName(String actName) {
        this.actName = actName == null ? null : actName.trim();
    }

    /**
     * 获取活动详情
     *
     * @return actDesc - 活动详情
     */
    public String getActDesc() {
        return actDesc;
    }

    /**
     * 设置活动详情
     *
     * @param actDesc 活动详情
     */
    public void setActDesc(String actDesc) {
        this.actDesc = actDesc == null ? null : actDesc.trim();
    }

    /**
     * 获取活动地址
     *
     * @return address - 活动地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置活动地址
     *
     * @param address 活动地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取活动文件地址
     *
     * @return filePath - 活动文件地址
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置活动文件地址
     *
     * @param filePath 活动文件地址
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath == null ? null : filePath.trim();
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
     * 获取活动开始时间
     *
     * @return beginTime - 活动开始时间
     */
    public Date getBeginTime() {
        return beginTime;
    }

    /**
     * 设置活动开始时间
     *
     * @param beginTime 活动开始时间
     */
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    /**
     * 获取活动结束时间
     *
     * @return endTime - 活动结束时间
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * 设置活动结束时间
     *
     * @param endTime 活动结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取报名截止时间
     *
     * @return applyEndTime - 报名截止时间
     */
    public Date getApplyEndTime() {
        return applyEndTime;
    }

    /**
     * 设置报名截止时间
     *
     * @param applyEndTime 报名截止时间
     */
    public void setApplyEndTime(Date applyEndTime) {
        this.applyEndTime = applyEndTime;
    }

    /**
     * 获取修改时间
     *
     * @return updateTime - 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     *
     * @param updateTime 修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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