package com.dwms.meeting.controller;

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
import com.dwms.meeting.domain.Meeting;
import com.dwms.meeting.domain.MtgUser;
import com.dwms.meeting.service.MeetingService;
import com.dwms.meeting.service.MtgUserService;

@Controller
public class MtgUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MtgUserService mtgUserService;
    
    @Autowired
    private MeetingService meetingService;
    
    @RequestMapping("mtgUser")
    @RequiresPermissions("mtgUser:list")
    public String index(Model model, Integer mtgId) {
    	model.addAttribute("mtgId", mtgId);
        return "meeting/mtgUser/mtgUser";
    }
    
    @Log("获取会议人员信息")
    @RequestMapping("mtgUser/list")
    @RequiresPermissions("mtgUser:list")
    @ResponseBody
    public Map<String, Object> mtgUserList(QueryRequest request, MtgUser mtgUser) {
        return super.selectByPageNumSize(request, () -> this.mtgUserService.findAllByMtgId(mtgUser));
    }

    @RequestMapping("mtgUser/excel")
    @ResponseBody
    public ResponseBo meetingExcel(MtgUser mtgUser) {
        try {
        	Meeting meeting = this.meetingService.selectByKey(mtgUser.getMtgId());
        	String sheetName = String.format("%s_会议人员表", meeting.getMtgName());
            List<MtgUser> list = this.mtgUserService.findAllByMtgId(mtgUser);
            return FileUtils.createExcelByPOIKit(sheetName, list, MtgUser.class);
        } catch (Exception e) {
            log.error("导出会议人员Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("mtgUser/csv")
    @ResponseBody
    public ResponseBo meetingCsv(MtgUser mtgUser) {
        try {
        	Meeting meeting = this.meetingService.selectByKey(mtgUser.getMtgId());
        	String sheetName = String.format("%s_会议人员表", meeting.getMtgName());
            List<MtgUser> list = this.mtgUserService.findAllByMtgId(mtgUser);
            return FileUtils.createCsv(sheetName, list, MtgUser.class);
        } catch (Exception e) {
            log.error("导出会议人员Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
}
