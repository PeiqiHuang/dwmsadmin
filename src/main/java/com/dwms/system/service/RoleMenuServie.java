package com.dwms.system.service;

import com.dwms.common.service.IService;
import com.dwms.system.domain.RoleMenu;

public interface RoleMenuServie extends IService<RoleMenu> {

	void deleteRoleMenusByRoleId(String roleIds);

	void deleteRoleMenusByMenuId(String menuIds);
}
