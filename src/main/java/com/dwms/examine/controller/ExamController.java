package com.dwms.examine.controller;

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
import com.dwms.examine.domain.Exam;
import com.dwms.examine.domain.vo.ExamWithUser;
import com.dwms.examine.service.ExamService;

@Controller
public class ExamController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExamService examService;

    @RequestMapping("exam")
    @RequiresPermissions("exam:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "examine/exam";
    }
    
    @RequestMapping("exam/getExam")
    @ResponseBody
    public ResponseBo getExam(Integer examId) {
        try {
            ExamWithUser exam = this.examService.findById(examId);
            return ResponseBo.ok(exam);
        } catch (Exception e) {
            log.error("获取考试失败", e);
            return ResponseBo.error("获取考试失败，请联系网站管理员！");
        }
    }

    @Log("获取考试信息")
    @RequestMapping("exam/list")
    @RequiresPermissions("exam:list")
    @ResponseBody
    public Map<String, Object> examList(QueryRequest request, Exam exam) {
    	if (!isAdmin()) {
    		exam.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.examService.findAllExams(exam));
    }

    @Log("新增考试")
    @RequiresPermissions("exam:add")
    @RequestMapping("exam/add")
    @ResponseBody
    public ResponseBo addExam(Exam exam, String[] users) {
    	if (null == exam.getPartyId()) {
    		exam.setPartyId(getPartyId());
    	}
        try {
            this.examService.addExam(exam, users);
            return ResponseBo.ok("新增考试成功！");
        } catch (Exception e) {
            log.error("新增考试失败", e);
            return ResponseBo.error("新增考试失败，请联系网站管理员！");
        }
    }
    
    @Log("复制考试")
    @RequiresPermissions("exam:add")
    @RequestMapping("exam/copy")
    @ResponseBody
    public ResponseBo copyExam(Exam exam, String[] users) {
        if (null == exam.getPartyId()) {
            exam.setPartyId(getPartyId());
        }
        try {
            this.examService.copyExam(exam, users);
            return ResponseBo.ok("复制考试成功！");
        } catch (Exception e) {
            log.error("复制考试失败", e);
            return ResponseBo.error("复制考试失败，请联系网站管理员！");
        }
    }

    @Log("修改考试")
    @RequiresPermissions("exam:update")
    @RequestMapping("exam/update")
    @ResponseBody
    public ResponseBo updateExam(Exam exam, String[] users) {
        try {
            return this.examService.updateExam(exam, users);
        } catch (Exception e) {
            log.error("修改考试失败", e);
            return ResponseBo.error("修改考试失败，请联系网站管理员！");
        }
    }
    
    @Log("删除考试")
    @RequiresPermissions("exam:delete")
    @RequestMapping("exam/delete")
    @ResponseBody
    public ResponseBo deleteExam(String ids) {
        try {
        	return this.examService.deleteExams(ids);
        } catch (Exception e) {
            log.error("删除考试失败", e);
            return ResponseBo.error("删除考试失败，请联系网站管理员！");
        }
    }
    
    @Log("发起补考")
    @RequiresPermissions("exam:update")
    @RequestMapping("exam/makeup")
    @ResponseBody
    public ResponseBo makeup(Integer examId) {
        try {
            return this.examService.makeup(examId);
        } catch (Exception e) {
            log.error("发起补考失败", e);
            return ResponseBo.error("发起补考失败，请联系网站管理员！");
        }
    }
}
