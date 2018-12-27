package com.dwms.due.controller;

import java.util.Map;

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
import com.dwms.due.domain.Due;
import com.dwms.due.domain.vo.DueWithUser;
import com.dwms.due.service.DueService;

@Controller
public class DueController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DueService dueService;

    @RequestMapping("due")
    @RequiresPermissions("due:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "due/due";
    }
    
    @RequestMapping("due/getDue")
    @ResponseBody
    public ResponseBo getDue(Integer dueId) {
        try {
            DueWithUser due = this.dueService.findById(dueId);
            return ResponseBo.ok(due);
        } catch (Exception e) {
            log.error("获取缴费项目失败", e);
            return ResponseBo.error("获取缴费项目失败，请联系网站管理员！");
        }
    }

    @Log("获取缴费项目信息")
    @RequestMapping("due/list")
    @RequiresPermissions("due:list")
    @ResponseBody
    public Map<String, Object> dueList(QueryRequest request, Due due) {
    	if (!isAdmin()) {
    		due.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.dueService.findAllDues(due));
    }

    @Log("新增缴费项目")
    @RequiresPermissions("due:add")
    @RequestMapping("due/add")
    @ResponseBody
    public ResponseBo addDue(Due due, String[] users) {
    	if (null == due.getPartyId()) {
    		due.setPartyId(getPartyId());
    	}
        try {
            this.dueService.addDue(due, users);
            return ResponseBo.ok("新增缴费项目成功！");
        } catch (Exception e) {
            log.error("新增缴费项目失败", e);
            return ResponseBo.error("新增缴费项目失败，请联系网站管理员！");
        }
    }
    
    @Log("修改缴费项目")
    @RequiresPermissions("due:update")
    @RequestMapping("due/update")
    @ResponseBody
    public ResponseBo updateDue(Due due, String[] users) {
        try {
            return this.dueService.updateDue(due, users);
        } catch (Exception e) {
            log.error("修改缴费项目失败", e);
            return ResponseBo.error("修改缴费项目失败，请联系网站管理员！");
        }
    }
    
    @Log("删除缴费项目")
    @RequiresPermissions("due:delete")
    @RequestMapping("due/delete")
    @ResponseBody
    public ResponseBo deleteDue(String ids) {
        try {
        	return this.dueService.deleteDues(ids);
        } catch (Exception e) {
            log.error("删除缴费项目失败", e);
            return ResponseBo.error("删除缴费项目失败，请联系网站管理员！");
        }
    }
    
}
