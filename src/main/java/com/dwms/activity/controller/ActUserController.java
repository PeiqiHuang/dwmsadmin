package com.dwms.activity.controller;

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

import com.dwms.activity.domain.ActUser;
import com.dwms.activity.domain.Activity;
import com.dwms.activity.service.ActUserService;
import com.dwms.activity.service.ActivityService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.util.FileUtils;

@Controller
public class ActUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActUserService actUserService;
    
    @Autowired
    private ActivityService activityService;
    
    
    @RequestMapping("actUser")
    @RequiresPermissions("activity:data")
    public String index(Model model, Integer actId) {
    	model.addAttribute("actId", actId);
    	
        return "activity/actUser/actUser";
    }
    
    @Log("获取活动人员信息")
    @RequestMapping("actUser/list")
    @RequiresPermissions("activity:data")
    @ResponseBody
    public Map<String, Object> actUserList(QueryRequest request, ActUser actUser) {
        return super.selectByPageNumSize(request, () -> this.actUserService.findAllByActId(actUser));
    }

    @RequestMapping("actUser/excel")
    @ResponseBody
    public ResponseBo dueExcel(ActUser actUser) {
        try {
        	Activity act = this.activityService.selectByKey(actUser.getActId());
        	String sheetName = String.format("%s_活动人员表", act.getActName());
            List<ActUser> list = this.actUserService.findAllByActId(actUser);
            return FileUtils.createExcelByPOIKit(sheetName, list, ActUser.class);
        } catch (Exception e) {
            log.error("导出活动人员Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("actUser/csv")
    @ResponseBody
    public ResponseBo dueCsv(ActUser actUser) {
        try {
        	Activity act = this.activityService.selectByKey(actUser.getActId());
        	String sheetName = String.format("%s_活动人员表", act.getActName());
            List<ActUser> list = this.actUserService.findAllByActId(actUser);
            return FileUtils.createCsv(sheetName, list, ActUser.class);
        } catch (Exception e) {
            log.error("导出活动人员Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
}
