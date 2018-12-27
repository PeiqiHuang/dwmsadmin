package com.dwms.examine.controller;

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
import com.dwms.examine.domain.Exam;
import com.dwms.examine.domain.ExamUser;
import com.dwms.examine.domain.form.ExamQuForm;
import com.dwms.examine.domain.form.ExamQuUserForm;
import com.dwms.examine.domain.vo.ExamQuWithQu;
import com.dwms.examine.service.ExamQuService;
import com.dwms.examine.service.ExamService;
import com.dwms.examine.service.ExamUserService;

@Controller
public class ExamUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExamUserService examUserService;
    
    @Autowired
    private ExamService examService;
    
    @Autowired
    private ExamQuService examQuService;
    
    @RequestMapping("examUser")
    @RequiresPermissions("exam:user")
    public String index(Model model, Integer examId) {
    	model.addAttribute("examId", examId);
    	
    	/*ExamQuForm qu = new ExamQuForm();
        qu.setExamId(examId);
        List<ExamQuWithQu> examQuList = this.examQuService.findAllByExamId(qu);
        model.addAttribute("examQuList", examQuList);*/
        
        return "examine/examUser/examUser";
    }
    
    @Log("获取考试人员信息")
    @RequestMapping("examUser/list")
    @RequiresPermissions("exam:user")
    @ResponseBody
    public Map<String, Object> examUserList(QueryRequest request, ExamUser examUser) {
        return super.selectByPageNumSize(request, () -> this.examUserService.findAllByExamId(examUser));
    }

    @RequestMapping("examUser/excel")
    @ResponseBody
    public ResponseBo examExcel(ExamUser examUser) {
        try {
        	Exam exam = this.examService.selectByKey(examUser.getExamId());
        	String sheetName = String.format("%s_考试人员表", exam.getExamName());
            List<ExamUser> list = this.examUserService.findAllByExamId(examUser);
            return FileUtils.createExcelByPOIKit(sheetName, list, ExamUser.class);
        } catch (Exception e) {
            log.error("导出考试人员Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("examUser/csv")
    @ResponseBody
    public ResponseBo examCsv(ExamUser examUser) {
        try {
        	Exam exam = this.examService.selectByKey(examUser.getExamId());
        	String sheetName = String.format("%s_考试人员表", exam.getExamName());
            List<ExamUser> list = this.examUserService.findAllByExamId(examUser);
            return FileUtils.createCsv(sheetName, list, ExamUser.class);
        } catch (Exception e) {
            log.error("导出考试人员Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
    
    //答卷详情
    @RequestMapping("examQuUser")
    @RequiresPermissions("exam:user")
    public String detail(Model model, Integer euId) {
        model.addAttribute("euId", euId);
        return "examine/examQuUser/examQuUser";
    }
    
    @Log("获取答卷详情信息")
    @RequestMapping("examQuUser/list")
    @RequiresPermissions("exam:user")
    @ResponseBody
    public Map<String, Object> examUserDetailList(QueryRequest request, ExamQuUserForm form) {
        ExamUser examUser = this.examUserService.selectByKey(form.getEuId());
        form.setExamId(examUser.getExamId());
        return super.selectByPageNumSize(request, () -> this.examUserService.findExamUserQu(form));
    }
    
    @Log("题目判分")
    @RequestMapping("examQuUser/judgeScore")
    @RequiresPermissions("exam:user")
    @ResponseBody
    public ResponseBo judgeScore(Integer equId, Integer score) {
        try {
            return this.examUserService.judgeScore(equId, score);
        } catch (Exception e) {
            log.error("题目判分失败", e);
            return ResponseBo.error("题目判分失败，请联系网站管理员！");
        }
    }
    
   /* @RequestMapping("examUser/getExam")
    @ResponseBody
    public ResponseBo getExam(Integer examId) {
        try {
            ExamWithUser exam = this.examService.findById(examId);
            return ResponseBo.ok(exam);
        } catch (Exception e) {
            log.error("获取考试失败", e);
            return ResponseBo.error("获取考试失败，请联系网站管理员！");
        }
    }*/
}
