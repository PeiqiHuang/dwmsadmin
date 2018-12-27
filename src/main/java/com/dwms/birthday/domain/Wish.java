package com.dwms.birthday.domain;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.dwms.common.annotation.ExportConfig;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "tb_birthday_wish")
@ToString
public class Wish {
    /**
     * 状态
     */
    public static final int STATUS_NO_VALID = 0;
    public static final int STATUS_VALID = 1;

    /**
     * 祝福编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer wishId;

    /**
     * 祝福人
     */
    private String fromUserId;

    /**
     * 被祝福人
     */
    private String toUserId;

    @Transient
    @ExportConfig(value = "祝福人")
    @Getter
    @Setter
    private String fromUserName;
    
    @Transient
    @ExportConfig(value = "生日党员")
    @Getter
    @Setter
    private String toUserName;
    
    /**
     * 祝福时间
     */
    @ExportConfig(value = "祝福时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date wishTime;

    /**
     * 祝福内容
     */
    @ExportConfig(value = "祝福内容")
    private String wishText;

    /**
     * 生日年分
     */
    @ExportConfig(value = "生日年分")
    private String wishYear;

    /**
     * 祝福类型：1书记寄语2生日感言3他人祝福
     */
    @ExportConfig(value = "祝福类型", convert = "s:1=书记寄语,2=生日感言,3=他人祝福")
    private Integer wishType;
    
    /**
     * 状态 0屏蔽 1启用
     */
    @ExportConfig(value = "状态", convert = "s:0=屏蔽,1=启用")
    private Integer status;
    
    /**
     * 获取祝福编号
     *
     * @return wishId - 祝福编号
     */
    public Integer getWishId() {
        return wishId;
    }

    /**
     * 设置祝福编号
     *
     * @param wishId 祝福编号
     */
    public void setWishId(Integer wishId) {
        this.wishId = wishId;
    }

    /**
     * 获取祝福人
     *
     * @return fromUserId - 祝福人
     */
    public String getFromUserId() {
        return fromUserId;
    }

    /**
     * 设置祝福人
     *
     * @param fromUserId 祝福人
     */
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId == null ? null : fromUserId.trim();
    }

    /**
     * 获取被祝福人
     *
     * @return toUserId - 被祝福人
     */
    public String getToUserId() {
        return toUserId;
    }

    /**
     * 设置被祝福人
     *
     * @param toUserId 被祝福人
     */
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId == null ? null : toUserId.trim();
    }

    /**
     * 获取祝福时间
     *
     * @return wishTime - 祝福时间
     */
    public Date getWishTime() {
        return wishTime;
    }

    /**
     * 设置祝福时间
     *
     * @param wishTime 祝福时间
     */
    public void setWishTime(Date wishTime) {
        this.wishTime = wishTime;
    }

    /**
     * 获取祝福内容
     *
     * @return wishText - 祝福内容
     */
    public String getWishText() {
        return wishText;
    }

    /**
     * 设置祝福内容
     *
     * @param wishText 祝福内容
     */
    public void setWishText(String wishText) {
        this.wishText = wishText == null ? null : wishText.trim();
    }

    /**
     * 获取生日年分
     *
     * @return wishYear - 生日年分
     */
    public String getWishYear() {
        return wishYear;
    }

    /**
     * 设置生日年分
     *
     * @param wishYear 生日年分
     */
    public void setWishYear(String wishYear) {
        this.wishYear = wishYear == null ? null : wishYear.trim();
    }

    /**
     * 获取祝福类型：1书记寄语2生日感言3他人祝福
     *
     * @return wishType - 祝福类型：1书记寄语2生日感言3他人祝福
     */
    public Integer getWishType() {
        return wishType;
    }

    /**
     * 设置祝福类型：1书记寄语2生日感言3他人祝福
     *
     * @param wishType 祝福类型：1书记寄语2生日感言3他人祝福
     */
    public void setWishType(Integer wishType) {
        this.wishType = wishType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}