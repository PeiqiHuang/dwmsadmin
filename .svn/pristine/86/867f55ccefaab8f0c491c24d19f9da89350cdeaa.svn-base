package com.dwms.system.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "tb_sys_user_role")
public class UserRole implements Serializable{
	
	private static final long serialVersionUID = -3166012934498268403L;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "ROLE_ID")
	private Long roleId;

	/**
	 * @return USER_ID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return ROLE_ID
	 */
	public Long getRoleId() {
		return roleId;
	}

	/**
	 * @param roleId
	 */
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
}