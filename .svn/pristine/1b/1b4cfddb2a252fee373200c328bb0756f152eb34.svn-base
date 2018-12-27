package com.dwms.common.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.dwms.common.domain.QueryRequest;
import com.dwms.system.domain.SysUser;
import com.dwms.user.domain.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class BaseController {
	
    protected static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    protected SysUser getCurrentUser() {
        return (SysUser) getSubject().getPrincipal();
    }
    
    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected void login(AuthenticationToken token) {
        getSubject().login(token);
    }

    private Map<String, Object> getDataTable(PageInfo<?> pageInfo) {
        Map<String, Object> rspData = new HashMap<>();
        rspData.put("rows", pageInfo.getList());
        rspData.put("total", pageInfo.getTotal());
        return rspData;
    }

    protected Map<String, Object> selectByPageNumSize(QueryRequest request, Supplier<?> s) {
        PageHelper.startPage(request.getPageNum(), request.getPageSize());
        PageInfo<?> pageInfo = new PageInfo<>((List<?>) s.get());
        PageHelper.clearPage();
        return getDataTable(pageInfo);
    }

    protected boolean isAdmin() {
        return getCurrentUser().getIsAdmin();
    }
    
    protected Integer getPartyId() {
    	return getCurrentUser().getPartyId();
    }

}
