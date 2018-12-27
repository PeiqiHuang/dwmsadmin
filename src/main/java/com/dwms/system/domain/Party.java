package com.dwms.system.domain;

import java.util.Date;

import javax.persistence.*;

import com.dwms.common.annotation.ExportConfig;

@Table(name = "tb_party_info")
public class Party {
	/**
	 * 账户状态
	 */
	public static final int STATUS_VALID = 1;
	public static final int STATUS_LOCK = 0;
	/**
	 * 来源
	 */
	public static final int SOURCE_ADMIN = 1;
	public static final int SOURCE_APP = 2;
	
    /**
     * 党支部ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ExportConfig(value = "编号")
    private Integer partyId;

    /**
     * 党支部名称
     */
    @ExportConfig(value = "党支部名称")
    private String partyName;

    /**
     * 上级党支部ID（没有为0）
     */
    private Integer parentId;

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
     * 来源 1.后台创建 2.app申请入驻
     */
    @ExportConfig(value = "来源", convert = "s:1=后台创建,2=app申请入驻")
    private Integer source;
    
    /**
     * 状态 1正常 0禁用
     */
    @ExportConfig(value = "状态", convert = "s:1=正常,0=禁用")
    private Integer status;

    /**
     * 创建时间
     */
    @ExportConfig(value = "创建时间", convert = "c:com.dwms.common.util.poi.convert.TimeConvert")
    private Date createTime;

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
     * 获取上级党支部ID（没有为0）
     *
     * @return parentId - 上级党支部ID（没有为0）
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置上级党支部ID（没有为0）
     *
     * @param parentId 上级党支部ID（没有为0）
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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
     * 获取来源 1.后台创建 2.app申请入驻
     *
     * @return source - 来源 1.后台创建 2.app申请入驻
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置来源 1.后台创建 2.app申请入驻
     *
     * @param source 来源 1.后台创建 2.app申请入驻
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		return "Party [partyId=" + partyId + ", partyName=" + partyName
				+ ", parentId=" + parentId + ", address=" + address
				+ ", contract=" + contract + ", mobile=" + mobile + ", email="
				+ email + ", source=" + source + ", status=" + status
				+ ", createTime=" + createTime + "]";
	}
    
}