package com.dwms.activity.domain;

import java.util.Date;

import javax.persistence.*;

import com.dwms.common.annotation.ExportConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_activity_user")
@ToString
public class ActUser {
    
    /**
     * 状态
     */
    public static final int STATUS_NOT_SIGN = -1;
    public static final int STATUS_CANCEL = 0;
    public static final int STATUS_SIGN = 1;
    
    /**
     * 逻辑id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer auId;

    /**
     * 活动id
     */
    private Integer actId;

    /**
     * 用户id
     */
    private String userId;

    
    @Transient
    @Getter
    @Setter
    @ExportConfig(value = "活动人员")
    private String userName;
    
    /**
     * 状态 -1未确认 0取消报名 1报名
     */
    @ExportConfig(value = "状态 ", convert = "s:-1=未确认,0=取消报名,1=报名")
    private Integer status;

    /**
     * 修改时间
     */
    @ExportConfig(value = "修改时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date updateTime;

    /**
     * 创建时间
     */
    @ExportConfig(value = "创建时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date createTime;

    /**
     * 获取逻辑id
     *
     * @return auId - 逻辑id
     */
    public Integer getAuId() {
        return auId;
    }

    /**
     * 设置逻辑id
     *
     * @param auId 逻辑id
     */
    public void setAuId(Integer auId) {
        this.auId = auId;
    }

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
     * 获取用户id
     *
     * @return userId - 用户id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     *
     * @param userId 用户id
     */
    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    /**
     * 获取状态 -1未确认 0取消报名 1报名
     *
     * @return status - 状态 -1未确认 0取消报名 1报名
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 -1未确认 0取消报名 1报名
     *
     * @param status 状态 -1未确认 0取消报名 1报名
     */
    public void setStatus(Integer status) {
        this.status = status;
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