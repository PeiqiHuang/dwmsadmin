package com.dwms.system.service;

import java.util.List;

import com.dwms.common.service.IService;
import com.dwms.system.domain.Role;
import com.dwms.system.domain.RoleWithMenu;

public interface RoleService extends IService<Role> {

	List<Role> findUserRole(String userName);

	List<Role> findAllRole(Role role);
	
	RoleWithMenu findRoleWithMenus(Long roleId);

	Role findByName(String roleName);

	void addRole(Role role, Long[] menuIds);
	
	void updateRole(Role role, Long[] menuIds);

	void deleteRoles(String roleIds);

    Role findRoleWithExcludeMenus(Long roleId);
}
