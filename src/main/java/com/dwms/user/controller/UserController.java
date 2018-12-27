package com.dwms.user.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dwms.common.annotation.Log;
import com.dwms.common.config.ImageConfig;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.util.FileUtils;
import com.dwms.user.domain.User;
import com.dwms.user.service.UserService;
import com.google.common.collect.Lists;

@Controller
public class UserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;
    @Resource
    private ImageConfig imageConfig;

    @RequestMapping("user")
    @RequiresPermissions("user:list")
    public String index(Model model) {
        System.out.println(imageConfig.getAccessPath());
    	model.addAttribute("isAdmin", isAdmin());
        return "user/user";
    }
    
    @RequestMapping("user/getUser")
    @ResponseBody
    public ResponseBo getUser(String userId) {
    	try {
    		User user = this.userService.findById(userId);
    		return ResponseBo.ok(user);
    	} catch (Exception e) {
    		log.error("获取党员失败", e);
    		return ResponseBo.error("获取党员失败，请联系网站管理员！");
    	}
    }

    @RequestMapping("user/getUsers")
    @ResponseBody
    public ResponseBo getUsers(Integer partyId) {
        if (null == partyId) {
            if (isAdmin()) {
                return ResponseBo.ok(Lists.newArrayList());
            } else {
                partyId = getPartyId();
            }
        }
        try {
        	User user = new User();
    		user.setPartyId(partyId);
        	List<User> users = this.userService.findAllUsers(user);
            return ResponseBo.ok(users);
        } catch (Exception e) {
            log.error("获取党员失败", e);
            return ResponseBo.error("获取党员失败，请联系网站管理员！");
        }
    }

    @Log("获取党员信息")
    @RequestMapping("user/list")
    @RequiresPermissions("user:list")
    @ResponseBody
    public Map<String, Object> userList(QueryRequest request, User user) {
    	if (!isAdmin()) {
    		user.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.userService.findAllUsers(user));
    }

    @RequestMapping("user/excel")
    @ResponseBody
    public ResponseBo userExcel(User user) {
        try {
            List<User> list = this.userService.findAllUsers(user);
            return FileUtils.createExcelByPOIKit("党员表", list, User.class);
        } catch (Exception e) {
            log.error("导出党员信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("user/csv")
    @ResponseBody
    public ResponseBo userCsv(User user) {
        try {
            List<User> list = this.userService.findAllUsers(user);
            return FileUtils.createCsv("党员表", list, User.class);
        } catch (Exception e) {
            log.error("导出党员信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
    
    @Log("批量上传党员")
    @RequiresPermissions("user:add")
    @RequestMapping("user/batch")
    @ResponseBody
    public ResponseBo batchUser(Integer partyId, MultipartFile batchFile) {
        if (null == partyId) {
            partyId = getPartyId();
        }
        try {
            return this.userService.batchUser(partyId, batchFile);
        } catch (Exception e) {
            log.error("批量上传党员失败", e);
            return ResponseBo.error("批量上传党员失败，请联系网站管理员！");
        }
    }

    @Log("新增党员")
    @RequiresPermissions("user:add")
    @RequestMapping("user/add")
    @ResponseBody
    public ResponseBo addUser(User user, MultipartFile file) {
    	if (null == user.getPartyId()) {
    		user.setPartyId(getPartyId());
    	}
        try {
            return this.userService.addUser(user, file);
        } catch (Exception e) {
            log.error("新增党员失败", e);
            return ResponseBo.error("新增党员失败，请联系网站管理员！");
        }
    }

    @Log("修改党员")
    @RequiresPermissions("user:update")
    @RequestMapping("user/update")
    @ResponseBody
    public ResponseBo updateUser(User user, MultipartFile file) {
        try {
            return this.userService.updateUser(user, file);
        } catch (Exception e) {
            log.error("修改党员失败", e);
            return ResponseBo.error("修改党员失败，请联系网站管理员！");
        }
    }

    @Log("重置密码")
    @RequiresPermissions("user:reset")
    @RequestMapping("user/reset")
    @ResponseBody
    public ResponseBo reset(String ids) {
        try {
            this.userService.resetPwd(ids);
            return ResponseBo.ok("重置密码成功！");
        } catch (Exception e) {
            log.error("重置密码失败", e);
            return ResponseBo.error("重置密码失败，请联系网站管理员！");
        }
    }
}
