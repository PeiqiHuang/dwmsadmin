package com.dwms.examine.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.dwms.examine.domain.ExamQu;
import com.dwms.examine.domain.Question;
import com.dwms.examine.domain.form.ExamQuDataForm;
import com.dwms.examine.domain.form.ExamQuForm;
import com.dwms.examine.domain.vo.ExamWithUser;
import com.dwms.examine.service.ExamQuService;
import com.dwms.examine.service.ExamService;
import com.dwms.examine.service.QuestionService;

@Controller
public class ExamQuController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExamQuService examQuService;
    
    @Autowired
    private ExamService examService;
    
    @Autowired
    private QuestionService questionService;
    
    @RequestMapping("examQu")
    @RequiresPermissions("examQu:list")
    public String index(Model model, Integer examId) {
    	model.addAttribute("examId", examId);
        return "examine/examQu/examQu";
    }
    
    @Log("获取试题试题信息")
    @RequestMapping("examQu/list")
    @RequiresPermissions("examQu:list")
    @ResponseBody
    public Map<String, Object> examQuList(QueryRequest request, ExamQuForm qu) {
        return super.selectByPageNumSize(request, () -> this.examQuService.findAllByExamId(qu));
    }
    
    /*@RequestMapping("examQu/getExamQuList")
    @ResponseBody
    public ResponseBo getExamQuList(Integer examId) {
        try {
            ExamQuForm qu = new ExamQuForm();
            qu.setExamId(examId);
            return ResponseBo.ok(this.examQuService.findAllByExamId(qu));
        } catch (Exception e) {
            log.error("获取试题失败", e);
            return ResponseBo.error("获取试题失败，请联系网站管理员！");
        }
    }*/
    
    @RequestMapping("examQu/getExamQu")
    @ResponseBody
    public ResponseBo getExamQu(Integer eqId) {
        try {
            return ResponseBo.ok(this.examQuService.findById(eqId));
        } catch (Exception e) {
            log.error("获取试题失败", e);
            return ResponseBo.error("获取试题失败，请联系网站管理员！");
        }
    }
    
    @RequestMapping("examQu/getTotalScore")
    @ResponseBody
    public ResponseBo getTotalScore(Integer examId) {
        try {
            return ResponseBo.ok(examQuService.getTotalScore(examId));
        } catch (Exception e) {
            log.error("获取总分失败", e);
            return ResponseBo.error("获取总分失败，请联系网站管理员！");
        }
    }
    
    @RequestMapping("examQu/searchQu")
    @ResponseBody
    public Map<String, Object> examQuList(QueryRequest request, Question qu, Integer examId) {
        if (StringUtils.isBlank(qu.getTitle())) return new HashMap<>();
        ExamWithUser exam = examService.findById(examId);
        if (null == exam) return new HashMap<>();
        qu.setPartyId(exam.getPartyId());
        return super.selectByPageNumSize(request, () -> this.questionService.searchQuestions(qu));
    }
    
    @Log("新增试题")
    @RequiresPermissions("examQu:add")
    @RequestMapping("examQu/add")
    @ResponseBody
    public ResponseBo addExamQu(ExamQu examQu) {
        try {
            return this.examQuService.addExamQu(examQu);
        } catch (Exception e) {
            log.error("新增试题失败", e);
            return ResponseBo.error("新增试题失败，请联系网站管理员！");
        }
    }

    @Log("修改试题")
    @RequiresPermissions("examQu:update")
    @RequestMapping("examQu/update")
    @ResponseBody
    public ResponseBo updateExamQu(ExamQu examQu) {
        try {
            return this.examQuService.updateExamQu(examQu);
        } catch (Exception e) {
            log.error("修改试题失败", e);
            return ResponseBo.error("修改试题失败，请联系网站管理员！");
        }
    }
    
    @Log("删除试题")
    @RequiresPermissions("examQu:delete")
    @RequestMapping("examQu/delete")
    @ResponseBody
    public ResponseBo deleteExamQu(String ids) {
        try {
            return this.examQuService.deleteExamQus(ids);
        } catch (Exception e) {
            log.error("删除试题失败", e);
            return ResponseBo.error("删除试题失败，请联系网站管理员！");
        }
    }
    
    //试题统计
    @RequestMapping("examQuData")
    @RequiresPermissions("exam:user")
    public String detail(Model model, Integer examId) {
        model.addAttribute("examId", examId);
        return "examine/examQuData/examQuData";
    }
    
    @Log("获取试题统计信息")
    @RequestMapping("examQuData/list")
    @RequiresPermissions("exam:user")
    @ResponseBody
    public Map<String, Object> examQuDataList(QueryRequest request, ExamQuForm form) {
        return super.selectByPageNumSize(request, () -> this.examQuService.findExamQuData(form));
    }
}
