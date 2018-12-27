package com.dwms.system.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.util.FileUtils;
import com.dwms.common.util.MD5Utils;
import com.dwms.system.domain.SysUser;
import com.dwms.system.domain.SysUserWithRole;
import com.dwms.system.service.SysUserService;

import java.util.List;
import java.util.Map;

@Controller
public class SysUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;

    private static final String ON = "on";

    @RequestMapping("sysuser")
    @RequiresPermissions("sysuser:list")
    public String index(Model model) {
        SysUser user = super.getCurrentUser();
        model.addAttribute("user", user);
        return "system/user/user";
    }

    @RequestMapping("sysuser/checkUserName")
    @ResponseBody
    public boolean checkUserName(String username, String oldusername) {
        if (StringUtils.isNotBlank(oldusername) && username.equalsIgnoreCase(oldusername)) {
            return true;
        }
        SysUser result = this.sysUserService.findByName(username);
        return result == null;
    }

    @RequestMapping("sysuser/getUser")
    @ResponseBody
    public ResponseBo getUser(String userId) {
        try {
            SysUser user = this.sysUserService.findById(userId);
            return ResponseBo.ok(user);
        } catch (Exception e) {
            log.error("获取用户失败", e);
            return ResponseBo.error("获取用户失败，请联系网站管理员！");
        }
    }

    @Log("获取用户信息")
    @RequestMapping("sysuser/list")
    @RequiresPermissions("sysuser:list")
    @ResponseBody
    public Map<String, Object> userList(QueryRequest request, SysUser user) {
        return super.selectByPageNumSize(request, () -> this.sysUserService.findUserWithParty(user, request));
    }

    @RequestMapping("sysuser/excel")
    @ResponseBody
    public ResponseBo userExcel(SysUser user) {
        try {
            List<SysUser> list = this.sysUserService.findUserWithParty(user, null);
            return FileUtils.createExcelByPOIKit("用户表", list, SysUser.class);
        } catch (Exception e) {
            log.error("导出用户信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("sysuser/csv")
    @ResponseBody
    public ResponseBo userCsv(SysUser user) {
        try {
            List<SysUser> list = this.sysUserService.findUserWithParty(user, null);
            return FileUtils.createCsv("用户表", list, SysUser.class);
        } catch (Exception e) {
            log.error("导出用户信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @RequestMapping("sysuser/regist")
    @ResponseBody
    public ResponseBo regist(SysUser user) {
        try {
            SysUser result = this.sysUserService.findByName(user.getUsername());
            if (result != null) {
                return ResponseBo.warn("该用户名已被使用！");
            }
            this.sysUserService.registUser(user);
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("注册失败", e);
            return ResponseBo.error("注册失败，请联系网站管理员！");
        }
    }

    @Log("更换主题")
    @RequestMapping("sysuser/theme")
    @ResponseBody
    public ResponseBo updateTheme(SysUser user) {
        try {
            this.sysUserService.updateTheme(user.getTheme(), user.getUsername());
            return ResponseBo.ok();
        } catch (Exception e) {
            log.error("修改主题失败", e);
            return ResponseBo.error();
        }
    }

    @Log("新增用户")
    @RequiresPermissions("sysuser:add")
    @RequestMapping("sysuser/add")
    @ResponseBody
    public ResponseBo addUser(SysUser user, Long[] roles) {
        try {
            if (ON.equalsIgnoreCase(user.getStatus()))
                user.setStatus(SysUser.STATUS_VALID);
            else
                user.setStatus(SysUser.STATUS_LOCK);
            this.sysUserService.addUser(user, roles);
            return ResponseBo.ok("新增用户成功！");
        } catch (Exception e) {
            log.error("新增用户失败", e);
            return ResponseBo.error("新增用户失败，请联系网站管理员！");
        }
    }

    @Log("修改用户")
    @RequiresPermissions("sysuser:update")
    @RequestMapping("sysuser/update")
    @ResponseBody
    public ResponseBo updateUser(SysUser user, Long[] rolesSelect) {
        try {
            if (ON.equalsIgnoreCase(user.getStatus()))
                user.setStatus(SysUser.STATUS_VALID);
            else
                user.setStatus(SysUser.STATUS_LOCK);
            this.sysUserService.updateUser(user, rolesSelect);
            return ResponseBo.ok("修改用户成功！");
        } catch (Exception e) {
            log.error("修改用户失败", e);
            return ResponseBo.error("修改用户失败，请联系网站管理员！");
        }
    }

    @Log("删除用户")
    @RequiresPermissions("sysuser:delete")
    @RequestMapping("sysuser/delete")
    @ResponseBody
    public ResponseBo deleteUsers(String ids) {
        try {
            this.sysUserService.deleteUsers(ids);
            return ResponseBo.ok("删除用户成功！");
        } catch (Exception e) {
            log.error("删除用户失败", e);
            return ResponseBo.error("删除用户失败，请联系网站管理员！");
        }
    }

    @RequestMapping("sysuser/checkPassword")
    @ResponseBody
    public boolean checkPassword(String password) {
        SysUser user = getCurrentUser();
        String encrypt = MD5Utils.md5(password, user.getUserId());
        return user.getPassword().equals(encrypt);
    }

    @RequestMapping("sysuser/updatePassword")
    @ResponseBody
    public ResponseBo updatePassword(String newPassword) {
        try {
            this.sysUserService.updatePassword(newPassword);
            return ResponseBo.ok("更改密码成功！");
        } catch (Exception e) {
            log.error("修改密码失败", e);
            return ResponseBo.error("更改密码失败，请联系网站管理员！");
        }
    }

    @RequestMapping("sysuser/profile")
    public String profileIndex(Model model) {
        SysUser user = super.getCurrentUser();
        user = this.sysUserService.findUserProfile(user);
        String ssex = user.getSsex();
        if (SysUser.SEX_MALE.equals(ssex)) {
            user.setSsex("性别：男");
        } else if (SysUser.SEX_FEMALE.equals(ssex)) {
            user.setSsex("性别：女");
        } else {
            user.setSsex("性别：保密");
        }
        model.addAttribute("user", user);
        return "system/user/profile";
    }

    @RequestMapping("sysuser/getUserProfile")
    @ResponseBody
    public ResponseBo getUserProfile(String userId) {
        try {
            SysUser user = new SysUser();
            user.setUserId(userId);
            return ResponseBo.ok(this.sysUserService.findUserProfile(user));
        } catch (Exception e) {
            log.error("获取用户信息失败", e);
            return ResponseBo.error("获取用户信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("sysuser/updateUserProfile")
    @ResponseBody
    public ResponseBo updateUserProfile(SysUser user) {
        try {
            this.sysUserService.updateUserProfile(user);
            return ResponseBo.ok("更新个人信息成功！");
        } catch (Exception e) {
            log.error("更新用户信息失败", e);
            return ResponseBo.error("更新用户信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("sysuser/changeAvatar")
    @ResponseBody
    public ResponseBo changeAvatar(String imgName) {
        try {
            String[] img = imgName.split("/");
            String realImgName = img[img.length - 1];
            SysUser user = getCurrentUser();
            user.setAvatar(realImgName);
            this.sysUserService.updateNotNull(user);
            return ResponseBo.ok("更新头像成功！");
        } catch (Exception e) {
            log.error("更换头像失败", e);
            return ResponseBo.error("更新头像失败，请联系网站管理员！");
        }
    }
    
    // 当前用户是否超管
    /*@RequestMapping("sysuser/isAdmin")
    @ResponseBody
    protected boolean isCurrentUserAdmin() {
    	SysUserWithRole userWithRole = sysUserService.findById(getCurrentUser().getUserId());
    	return userWithRole.isAdmin();
    }*/
}
