package com.dwms.due.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.dwms.common.annotation.ExportConfig;

@Table(name = "tb_due_user")
@ToString
public class DueUser {
    /**
     * 状态
     */
    public static final int STATUS_NO = 0;
    public static final int STATUS_YES = 1;
    public static final int STATUS_CONFIRM = 2;
    /**
     * 缴费方式
     */
    public static final int PAYWAY_WEIXIN = 1;
    public static final int PAYWAY_BANK = 2;
    public static final int PAYWAY_ZHIFUBAO = 3;
    public static final int PAYWAY_CASH = 4;
    /**
     * 党费党员id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer duId;

    /**
     * 党费id
     */
    private Integer dueId;

    /**
     * 用户id
     */
    private String userId;
    
    @Transient
    @ExportConfig(value = "缴费人员")
    @Setter
    @Getter
    private String userName;

    /**
     * 缴费金额
     */
    @ExportConfig(value = "缴费金额（元）")
    private Integer due;

    /**
     * 缴费方式1微信 2银行卡转账 3支付宝 4现金支付
     */
    @ExportConfig(value = "缴费方式", convert = "s:1=微信,2=银行转账,3=支付宝,4=现金支付")
    private Integer payWay;

    /**
     * 缴费状态 0未交费 1已交费 2管理员确认
     */
    @ExportConfig(value = "缴费状态", convert = "s:0=未交费,1已交费,2=管理员确认")
    private Integer status;

    /**
     * 上传缴费凭证图片地址
     */
    private String imgPath;

    /**
     * 缴费时间
     */
    @ExportConfig(value = "缴费时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date dueTime;
    
    /**
     * 创建时间
     */
    @ExportConfig(value = "创建时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date createTime;

    /**
     * 获取党费党员id
     *
     * @return duId - 党费党员id
     */
    public Integer getDuId() {
        return duId;
    }

    /**
     * 设置党费党员id
     *
     * @param duId 党费党员id
     */
    public void setDuId(Integer duId) {
        this.duId = duId;
    }

    /**
     * 获取党费id
     *
     * @return dueId - 党费id
     */
    public Integer getDueId() {
        return dueId;
    }

    /**
     * 设置党费id
     *
     * @param dueId 党费id
     */
    public void setDueId(Integer dueId) {
        this.dueId = dueId;
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
     * 获取缴费金额
     *
     * @return due - 缴费金额
     */
    public Integer getDue() {
        return due;
    }

    /**
     * 设置缴费金额
     *
     * @param due 缴费金额
     */
    public void setDue(Integer due) {
        this.due = due;
    }

    /**
     * 获取1微信 2银行卡转账 3支付宝 4现金支付
     *
     * @return payWay - 1微信 2银行卡转账 3支付宝 4现金支付
     */
    public Integer getPayWay() {
        return payWay;
    }

    /**
     * 设置1微信 2银行卡转账 3支付宝 4现金支付
     *
     * @param payWay 1微信 2银行卡转账 3支付宝 4现金支付
     */
    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    /**
     * 获取交费状态 0未交费 1已交费党员确认 2管理员确认
     *
     * @return status - 交费状态 0未交费 1已交费党员确认 2管理员确认
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置交费状态 0未交费 1已交费党员确认 2管理员确认
     *
     * @param status 交费状态 0未交费 1已交费党员确认 2管理员确认
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取上传缴费凭证图片地址
     *
     * @return imgPath - 上传缴费凭证图片地址
     */
    public String getImgPath() {
        return imgPath;
    }

    /**
     * 设置上传缴费凭证图片地址
     *
     * @param imgPath 上传缴费凭证图片地址
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath == null ? null : imgPath.trim();
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

    public Date getDueTime() {
        return dueTime;
    }

    public void setDueTime(Date dueTime) {
        this.dueTime = dueTime;
    }
}