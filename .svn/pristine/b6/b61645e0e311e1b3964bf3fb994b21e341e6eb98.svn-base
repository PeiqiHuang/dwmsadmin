package com.dwms.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.dwms.common.domain.QueryRequest;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.MD5Utils;
import com.dwms.common.util.UUIDUtils;
import com.dwms.system.dao.SysUserMapper;
import com.dwms.system.dao.UserRoleMapper;
import com.dwms.system.domain.SysUser;
import com.dwms.system.domain.UserRole;
import com.dwms.system.domain.SysUserWithRole;
import com.dwms.system.service.UserRoleService;
import com.dwms.system.service.SysUserService;

@Service("sysUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SysUserServiceImpl extends BaseService<SysUser> implements SysUserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public SysUser findByName(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        List<SysUser> list = this.selectByExample(example);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<SysUser> findUserWithParty(SysUser user, QueryRequest request) {
        try {
            return this.sysUserMapper.findSysUserWithParty(user);
        } catch (Exception e) {
            log.error("error", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void registUser(SysUser user) {
    	user.setUserId(UUIDUtils.generateUUID());
        user.setCrateTime(new Date());
        user.setTheme(SysUser.DEFAULT_THEME);
        user.setAvatar(SysUser.DEFAULT_AVATAR);
        user.setSsex(SysUser.SEX_UNKNOW);
        user.setPassword(MD5Utils.md5(user.getPassword(), user.getUserId()));
        this.save(user);
        UserRole ur = new UserRole();
        ur.setUserId(user.getUserId());
        ur.setRoleId(3L);
        this.userRoleMapper.insert(ur);
    }

    @Override
    @Transactional
    public void updateTheme(String theme, String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andCondition("username=", userName);
        SysUser user = new SysUser();
        user.setTheme(theme);
        this.sysUserMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void addUser(SysUser user, Long[] roles) {
    	user.setUserId(UUIDUtils.generateUUID());
        user.setCrateTime(new Date());
        user.setTheme(SysUser.DEFAULT_THEME);
        user.setAvatar(SysUser.DEFAULT_AVATAR);
        user.setPassword(MD5Utils.md5(user.getPassword(), user.getUserId()));
        this.save(user);
        setUserRoles(user, roles);
    }

    private void setUserRoles(SysUser user, Long[] roles) {
        Arrays.stream(roles).forEach(roleId -> {
            UserRole ur = new UserRole();
            ur.setUserId(user.getUserId());
            ur.setRoleId(roleId);
            this.userRoleMapper.insert(ur);
        });
    }

    @Override
    @Transactional
    public void updateUser(SysUser user, Long[] roles) {
        user.setPassword(null);
        user.setUsername(null);
        user.setModifyTime(new Date());
        this.updateNotNull(user);
        Example example = new Example(UserRole.class);
        example.createCriteria().andCondition("user_id=", user.getUserId());
        this.userRoleMapper.deleteByExample(example);
        setUserRoles(user, roles);
    }

    @Override
    @Transactional
    public void deleteUsers(String userIds) {
        List<String> list = Arrays.asList(userIds.split(","));
        this.batchDelete(list, "userId", SysUser.class);

        this.userRoleService.deleteUserRolesByUserId(userIds);
    }

    @Override
    @Transactional
    public void updateLoginTime(String userName) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andCondition("lower(username)=", userName.toLowerCase());
        SysUser user = new SysUser();
        user.setLastLoginTime(new Date());
        this.sysUserMapper.updateByExampleSelective(user, example);
    }

    @Override
    @Transactional
    public void updatePassword(String password) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Example example = new Example(SysUser.class);
        example.createCriteria().andCondition("username=", user.getUsername());
        String newPassword = MD5Utils.md5(password, user.getUserId());
        user.setPassword(newPassword);
        this.sysUserMapper.updateByExampleSelective(user, example);
    }

    @Override
    public SysUserWithRole findById(String userId) {
        List<SysUserWithRole> list = this.sysUserMapper.findSysUserWithRole(userId);
        List<Long> roleList = new ArrayList<>();
        for (SysUserWithRole uwr : list) {
            roleList.add(uwr.getRoleId());
        }
        if (list.isEmpty()) {
            return null;
        }
        SysUserWithRole userWithRole = list.get(0);
        userWithRole.setRoleIds(roleList);
        return userWithRole;
    }

    @Override
    public SysUser findUserProfile(SysUser user) {
        return this.sysUserMapper.findSysUserProfile(user);
    }

    @Override
    @Transactional
    public void updateUserProfile(SysUser user) {
        user.setUsername(null);
        user.setPassword(null);
        // 党支部管理员不能修改自己所属党支部 
        /*if (user.getPartyId() == null)
            user.setPartyId(0);*/
        this.updateNotNull(user);
    }

}
