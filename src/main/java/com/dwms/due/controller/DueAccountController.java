package com.dwms.due.controller;

import java.util.List;
import java.util.Map;

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
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.course.domain.CourseCat;
import com.dwms.due.domain.DueAccount;
import com.dwms.due.service.DueAccountService;
import com.dwms.due.service.DueService;
import com.google.common.collect.Lists;

@Controller
public class DueAccountController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DueAccountService dueAccountService;
    
    @Autowired
    private DueService dueService;
    
    
    @RequestMapping("dueAccount")
    @RequiresPermissions("due:account")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
    	
        return "due/account/account";
    }
    
    @Log("获取账户信息")
    @RequestMapping("dueAccount/list")
    @RequiresPermissions("due:account")
    @ResponseBody
    public Map<String, Object> dueAccountList(QueryRequest request, DueAccount dueAccount) {
        if (!isAdmin()) {
            dueAccount.setPartyId(getPartyId());
        }
        return super.selectByPageNumSize(request, () -> this.dueAccountService.findAll(dueAccount));
    }
    
    @RequestMapping("dueAccount/getAccount")
    @ResponseBody
    public ResponseBo getAccount(Integer accId) {
        try {
            DueAccount account = this.dueAccountService.findById(accId);
            return ResponseBo.ok(account);
        } catch (Exception e) {
            log.error("获取账户信息失败", e);
            return ResponseBo.error("获取账户信息失败，请联系网站管理员！");
        }
    }
    
    @RequestMapping("dueAccount/getAccounts")
    @ResponseBody
    public ResponseBo getAccounts(Integer partyId) {
        if (null == partyId) {
            if (isAdmin()) {
                return ResponseBo.ok(Lists.newArrayList());
            } else {
                partyId = getPartyId();
            }
        }
        try {
            DueAccount account = new DueAccount();
            account.setPartyId(partyId);
            account.setStatus(DueAccount.STATUS_VALID);
            List<DueAccount> accounts = this.dueAccountService.findAll(account);
            return ResponseBo.ok(accounts);
        } catch (Exception e) {
            log.error("获取账户信息列表失败", e);
            return ResponseBo.error("获取账户信息列表失败，请联系网站管理员！");
        }
    }
    
    @Log("新增账户信息")
    @RequiresPermissions("due:account")
    @RequestMapping("dueAccount/add")
    @ResponseBody
    public ResponseBo addCourse(DueAccount account, MultipartFile wechat, MultipartFile alipay) {
        if (null == account.getPartyId()) {
            account.setPartyId(getPartyId());
        }
        try {
            account.setWechatImg(wechat);
            account.setAlipayImg(alipay);
            return this.dueAccountService.addAccount(account);
        } catch (Exception e) {
            log.error("新增账户信息失败", e);
            return ResponseBo.error("新增账户信息失败，请联系网站管理员！");
        }
    }

    @Log("修改账户信息")
    @RequiresPermissions("due:account")
    @RequestMapping("dueAccount/update")
    @ResponseBody
    public ResponseBo updateCourse(DueAccount account, MultipartFile wechat, MultipartFile alipay) {
        try {
            account.setWechatImg(wechat);
            account.setAlipayImg(alipay);
            return this.dueAccountService.updateAccount(account);
        } catch (Exception e) {
            log.error("修改账户信息失败", e);
            return ResponseBo.error("修改账户信息失败，请联系网站管理员！");
        }
    }
    
    @Log("删除账户信息")
    @RequiresPermissions("due:account")
    @RequestMapping("dueAccount/delete")
    @ResponseBody
    public ResponseBo deleteCourse(String ids) {
        try {
            return this.dueAccountService.deleteAccounts(ids);
        } catch (Exception e) {
            log.error("删除账户信息失败", e);
            return ResponseBo.error("删除账户信息失败，请联系网站管理员！");
        }
    }
}
