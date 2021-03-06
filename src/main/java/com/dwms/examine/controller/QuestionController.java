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
import com.dwms.examine.domain.Question;
import com.dwms.examine.service.QuestionService;

@Controller
public class QuestionController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionService questionService;

    @RequestMapping("question")
    @RequiresPermissions("question:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "examine/question/question";
    }
    
    @RequestMapping("question/getQuestion")
    @ResponseBody
    public ResponseBo getQuestion(Integer quId) {
        try {
            Question question = this.questionService.selectByKey(quId);
            return ResponseBo.ok(question);
        } catch (Exception e) {
            log.error("获取题目失败", e);
            return ResponseBo.error("获取题目失败，请联系网站管理员！");
        }
    }

    @Log("获取题目信息")
    @RequestMapping("question/list")
    @RequiresPermissions("question:list")
    @ResponseBody
    public Map<String, Object> questionList(QueryRequest request, Question question) {
    	if (!isAdmin()) {
    		question.setPartyId(getPartyId());
    	}
        return super.selectByPageNumSize(request, () -> this.questionService.findAllQuestions(question));
    }

    @Log("新增题目")
    @RequiresPermissions("question:add")
    @RequestMapping("question/add")
    @ResponseBody
    public ResponseBo addQuestion(Question question) {
    	if (null == question.getPartyId()) {
    		question.setPartyId(getPartyId());
    	}
        try {
            this.questionService.addQuestion(question);
            return ResponseBo.ok("新增题目成功！");
        } catch (Exception e) {
            log.error("新增题目失败", e);
            return ResponseBo.error("新增题目失败，请联系网站管理员！");
        }
    }

    @Log("修改题目")
    @RequiresPermissions("question:update")
    @RequestMapping("question/update")
    @ResponseBody
    public ResponseBo updateQuestion(Question question) {
        try {
            this.questionService.updateQuestion(question);
            return ResponseBo.ok("修改题目成功！");
        } catch (Exception e) {
            log.error("修改题目失败", e);
            return ResponseBo.error("修改题目失败，请联系网站管理员！");
        }
    }
    
    @Log("删除题目")
    @RequiresPermissions("question:delete")
    @RequestMapping("question/delete")
    @ResponseBody
    public ResponseBo deleteQuestion(String ids) {
        try {
        	return this.questionService.deleteQuestions(ids);
        } catch (Exception e) {
            log.error("删除题目失败", e);
            return ResponseBo.error("删除题目失败，请联系网站管理员！");
        }
    }
}
