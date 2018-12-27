package com.dwms.activity.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.activity.domain.Activity;
import com.dwms.activity.domain.vo.ActivityWithUser;
import com.dwms.activity.service.ActivityService;
import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;

@Controller
public class ActivityController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActivityService activityService;

    @RequestMapping("activity")
    @RequiresPermissions("activity:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "activity/activity";
    }
    
    @RequestMapping("activity/getActivity")
    @ResponseBody
    public ResponseBo getActivity(Integer actId) {
        try {
            ActivityWithUser activity = this.activityService.findById(actId);
            return ResponseBo.ok(activity);
        } catch (Exception e) {
            log.error("获取活动失败", e);
            return ResponseBo.error("获取活动失败，请联系网站管理员！");
        }
    }

    @Log("获取活动信息")
    @RequestMapping("activity/list")
    @RequiresPermissions("activity:list")
    @ResponseBody
    public Map<String, Object> activityList(QueryRequest request, Activity activity) {
    	if (!isAdmin()) {
    		activity.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.activityService.findAllActivitys(activity));
    }

    @Log("新增活动")
    @RequiresPermissions("activity:add")
    @RequestMapping("activity/add")
    @ResponseBody
    public ResponseBo addActivity(Activity activity, String[] users) {
    	if (null == activity.getPartyId()) {
    		activity.setPartyId(getPartyId());
    	}
        try {
            return this.activityService.addActivity(activity, users);
        } catch (Exception e) {
            log.error("新增活动失败", e);
            return ResponseBo.error("新增活动失败，请联系网站管理员！");
        }
    }
    
    @Log("修改活动")
    @RequiresPermissions("activity:update")
    @RequestMapping("activity/update")
    @ResponseBody
    public ResponseBo updateActivity(Activity activity, String[] users) {
        try {
            return this.activityService.updateActivity(activity, users);
        } catch (Exception e) {
            log.error("修改活动失败", e);
            return ResponseBo.error("修改活动失败，请联系网站管理员！");
        }
    }
    
    @Log("删除活动")
    @RequiresPermissions("activity:delete")
    @RequestMapping("activity/delete")
    @ResponseBody
    public ResponseBo deleteActivity(String ids) {
        try {
        	return this.activityService.deleteActivitys(ids);
        } catch (Exception e) {
            log.error("删除活动失败", e);
            return ResponseBo.error("删除活动失败，请联系网站管理员！");
        }
    }
    
}
