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

import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.util.FileUtils;
import com.dwms.due.domain.Due;
import com.dwms.due.domain.DueUser;
import com.dwms.due.service.DueService;
import com.dwms.due.service.DueUserService;

@Controller
public class DueUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DueUserService dueUserService;
    
    @Autowired
    private DueService dueService;
    
    
    @RequestMapping("dueUser")
    @RequiresPermissions("due:data")
    public String index(Model model, Integer dueId) {
    	model.addAttribute("dueId", dueId);
    	
        return "due/dueUser/dueUser";
    }
    
    @Log("获取缴费人员信息")
    @RequestMapping("dueUser/list")
    @RequiresPermissions("due:data")
    @ResponseBody
    public Map<String, Object> dueUserList(QueryRequest request, DueUser dueUser) {
        return super.selectByPageNumSize(request, () -> this.dueUserService.findAllByDueId(dueUser));
    }

    @RequestMapping("dueUser/excel")
    @ResponseBody
    public ResponseBo dueExcel(DueUser dueUser) {
        try {
        	Due due = this.dueService.selectByKey(dueUser.getDueId());
        	String sheetName = String.format("%s_缴费人员表", due.getTitle());
            List<DueUser> list = this.dueUserService.findAllByDueId(dueUser);
            return FileUtils.createExcelByPOIKit(sheetName, list, DueUser.class);
        } catch (Exception e) {
            log.error("导出缴费人员Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("dueUser/csv")
    @ResponseBody
    public ResponseBo dueCsv(DueUser dueUser) {
        try {
        	Due due = this.dueService.selectByKey(dueUser.getDueId());
        	String sheetName = String.format("%s_缴费人员表", due.getTitle());
            List<DueUser> list = this.dueUserService.findAllByDueId(dueUser);
            return FileUtils.createCsv(sheetName, list, DueUser.class);
        } catch (Exception e) {
            log.error("导出缴费人员Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
    
    @Log("管理员确认已缴费")
    @RequestMapping("dueUser/confirm")
    @RequiresPermissions("due:data")
    @ResponseBody
    public ResponseBo confirm(Integer duId) {
        try {
            return this.dueUserService.confirm(duId);
        } catch (Exception e) {
            log.error("确认缴费失败", e);
            return ResponseBo.error("确认缴费失败，请联系网站管理员！");
        }
    }
}
