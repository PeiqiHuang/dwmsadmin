package com.dwms.system.domain;

import java.util.Date;

import javax.persistence.*;

import com.dwms.common.annotation.ExportConfig;

@Table(name = "tb_party_apply")
public class Apply {
	/**
	 * 账户状态
	 */
	public static final int STATUS_PASS = 1;
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_FAIL = -1;
	
    /**
     * 申请ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExportConfig(value = "申请ID")
    private Integer applyId;

    /**
     * 党支部名称
     */
    @ExportConfig(value = "党支部名称")
    private String partyName;

    /**
     * 党组织人数
     */
    @ExportConfig(value = "党组织人数")
    private Integer memNum;

    /**
     * 所在地址
     */
    @ExportConfig(value = "所在地址")
    private String address;

    /**
     * 联系人
     */
    @ExportConfig(value = "联系人")
    private String contract;

    /**
     * 联系电话
     */
    @ExportConfig(value = "联系电话")
    private String mobile;

    /**
     * 邮箱
     */
    @ExportConfig(value = "邮箱")
    private String email;

    /**
     * 状态 1审核通过（创建党支部） 0待审核 -1审核失败
     */
    @ExportConfig(value = "状态", convert = "s:1=审核通过,0=待审核,-1=审核失败")
    private Integer status;
    
    /**
     * 审核通过后对应的党组织ID
     */
    private Integer partyId;

    /**
     * 创建时间
     */
    @ExportConfig(value = "创建时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date createTime;

    /**
     * 获取申请ID
     *
     * @return applyId - 申请ID
     */
    public Integer getApplyId() {
        return applyId;
    }

    /**
     * 设置申请ID
     *
     * @param applyId 申请ID
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }

    /**
     * 获取党支部名称
     *
     * @return partyName - 党支部名称
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * 设置党支部名称
     *
     * @param partyName 党支部名称
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName == null ? null : partyName.trim();
    }

    /**
     * 获取党组织人数
     *
     * @return memNum - 党组织人数
     */
    public Integer getMemNum() {
        return memNum;
    }

    /**
     * 设置党组织人数
     *
     * @param memNum 党组织人数
     */
    public void setMemNum(Integer memNum) {
        this.memNum = memNum;
    }

    /**
     * 获取所在地址
     *
     * @return address - 所在地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置所在地址
     *
     * @param address 所在地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取联系人
     *
     * @return contract - 联系人
     */
    public String getContract() {
        return contract;
    }

    /**
     * 设置联系人
     *
     * @param contract 联系人
     */
    public void setContract(String contract) {
        this.contract = contract == null ? null : contract.trim();
    }

    /**
     * 获取联系电话
     *
     * @return mobile - 联系电话
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置联系电话
     *
     * @param mobile 联系电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取状态 1审核通过（创建党支部） 0待审核 -1审核失败
     *
     * @return status - 状态 1审核通过（创建党支部） 0待审核 -1审核失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1审核通过（创建党支部） 0待审核 -1审核失败
     *
     * @param status 状态 1审核通过（创建党支部） 0待审核 -1审核失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getPartyId() {
		return partyId;
	}

	public void setPartyId(Integer partyId) {
		this.partyId = partyId;
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
		return "Apply [applyId=" + applyId + ", partyName=" + partyName
				+ ", memNum=" + memNum + ", address=" + address + ", contract="
				+ contract + ", mobile=" + mobile + ", email=" + email
				+ ", status=" + status + ", partyId=" + partyId
				+ ", createTime=" + createTime + "]";
	}
    
}