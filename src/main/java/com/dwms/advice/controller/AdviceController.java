package com.dwms.advice.controller;

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
import com.dwms.advice.domain.Advice;
import com.dwms.advice.service.AdviceService;

@Controller
public class AdviceController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdviceService adviceService;

    @RequestMapping("advice")
    @RequiresPermissions("advice:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "advice/advice";
    }
    
    @Log("获取反馈信息")
    @RequestMapping("advice/list")
    @RequiresPermissions("advice:list")
    @ResponseBody
    public Map<String, Object> adviceList(QueryRequest request, Advice advice) {
        return super.selectByPageNumSize(request, () -> this.adviceService.findAllAdvices(advice));
    }

}
