package com.dwms.system.dao;

import java.util.List;

import com.dwms.common.config.MyMapper;
import com.dwms.system.domain.Role;
import com.dwms.system.domain.RoleWithMenu;

public interface RoleMapper extends MyMapper<Role> {
	
	List<Role> findUserRole(String userName);
	
	List<RoleWithMenu> findById(Long roleId);
}