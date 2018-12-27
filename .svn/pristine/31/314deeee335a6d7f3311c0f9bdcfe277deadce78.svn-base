package com.dwms.system.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.dwms.common.domain.QueryRequest;
import com.dwms.common.service.IService;
import com.dwms.system.domain.SysUser;
import com.dwms.system.domain.SysUserWithRole;

import java.util.List;

@CacheConfig(cacheNames = "SysUserService")
public interface SysUserService extends IService<SysUser> {

    SysUserWithRole findById(String userId);

    SysUser findByName(String userName);

    //@Cacheable(key = "#p0.toString() + (#p1 != null ? #p1.toString() : '')")
    List<SysUser> findUserWithParty(SysUser user, QueryRequest request);

    //@CacheEvict(key = "#p0", allEntries = true)
    void registUser(SysUser user);

    void updateTheme(String theme, String userName);

    //@CacheEvict(allEntries = true)
    void addUser(SysUser user, Long[] roles);

    //@CacheEvict(key = "#p0", allEntries = true)
    void updateUser(SysUser user, Long[] roles);

    //@CacheEvict(key = "#p0", allEntries = true)
    void deleteUsers(String userIds);

    void updateLoginTime(String userName);

    void updatePassword(String password);

    SysUser findUserProfile(SysUser user);

    void updateUserProfile(SysUser user);
}
