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
import org.springframework.web.multipart.MultipartFile;

import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.meeting.domain.MtgSumUser;
import com.dwms.meeting.domain.MtgSummary;
import com.dwms.meeting.domain.form.MtgSummaryForm;
import com.dwms.meeting.domain.vo.MeetingWithUser;
import com.dwms.meeting.domain.vo.MtgSumWithUser;
import com.dwms.meeting.service.MeetingService;
import com.dwms.meeting.service.MtgSummaryService;

@Controller
public class MtgSummaryController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MtgSummaryService mtgSummaryService;
    
    @Autowired
    private MeetingService meetingService;
    
    @RequestMapping("mtgSummary")
    @RequiresPermissions("mtgSummary:list")
    public String index(Model model, Integer mtgId) {
    	model.addAttribute("mtgId", mtgId);
    	MeetingWithUser meeting = meetingService.findById(mtgId);
    	model.addAttribute("partyId", meeting.getPartyId());
        return "meeting/mtgSummary/mtgSummary";
    }
    
    @Log("获取会议纪要信息")
    @RequestMapping("mtgSummary/list")
    @RequiresPermissions("mtgSummary:list")
    @ResponseBody
    public Map<String, Object> mtgSummaryList(QueryRequest request, MtgSummary mtgSummary) {
        return super.selectByPageNumSize(request, () -> this.mtgSummaryService.findAll(mtgSummary));
    }
    @RequestMapping("mtgSummary/getMtgSummary")
    @ResponseBody
    public ResponseBo getMtgSummary(Integer sumId) {
        try {
        	MtgSumWithUser sum = this.mtgSummaryService.findMtgSumById(sumId);
            return ResponseBo.ok(sum);
        } catch (Exception e) {
            log.error("获取会议纪要失败", e);
            return ResponseBo.error("获取会议纪要失败，请联系网站管理员！");
        }
    }
    
    @RequestMapping("mtgSummary/getMtgSumUser")
    @ResponseBody
    public ResponseBo getMtgSumUser(Integer sumId) {
    	try {
    		List<MtgSumUser> list = this.mtgSummaryService.findMtgSumUsers(sumId);
    		return ResponseBo.ok(list);
    	} catch (Exception e) {
    		log.error("获取会议纪要查看人员失败", e);
    		return ResponseBo.error("获取会议纪要查看人员失败，请联系网站管理员！");
    	}
    }
    
    @Log("新增会议纪要")
    @RequiresPermissions("mtgSummary:add")
    @RequestMapping("mtgSummary/add")
    @ResponseBody
    public ResponseBo addMtgSummary(MtgSummaryForm sum, MultipartFile[] file) {
        try {
            sum.setFiles(file);
            return this.mtgSummaryService.addMtgSummary(sum);
//            return ResponseBo.ok("新增会议纪要成功！");
        } catch (Exception e) {
            log.error("新增会议纪要失败", e);
            return ResponseBo.error("新增会议纪要失败，请联系网站管理员！");
        }
    }

    @Log("修改会议纪要")
    @RequiresPermissions("mtgSummary:update")
    @RequestMapping("mtgSummary/update")
    @ResponseBody
    public ResponseBo updateMtgSummary(MtgSummaryForm sum, MultipartFile[] file) {
        try {
            sum.setFiles(file);
            return this.mtgSummaryService.updateMtgSummary(sum);
            //return ResponseBo.ok("修改会议纪要成功！");
        } catch (Exception e) {
            log.error("修改会议纪要失败", e);
            return ResponseBo.error("修改会议纪要失败，请联系网站管理员！");
        }
    }
    
    @Log("删除会议纪要")
    @RequiresPermissions("mtgSummary:delete")
    @RequestMapping("mtgSummary/delete")
    @ResponseBody
    public ResponseBo deleteSum(String ids) {
        try {
        	return this.mtgSummaryService.deleteMtgSummarys(ids);
        } catch (Exception e) {
            log.error("删除会议纪要失败", e);
            return ResponseBo.error("删除会议纪要失败，请联系网站管理员！");
        }
    }

}
