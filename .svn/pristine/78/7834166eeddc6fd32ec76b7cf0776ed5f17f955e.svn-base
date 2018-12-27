package com.dwms.help.controller;

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
import com.dwms.help.domain.Help;
import com.dwms.help.service.HelpService;

@Controller
public class HelpController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HelpService helpService;

    @RequestMapping("help")
    @RequiresPermissions("help:list")
    public String index(Model model) {
    	model.addAttribute("isAdmin", isAdmin());
        return "help/help";
    }
    
    @RequestMapping("help/getHelp")
    @ResponseBody
    public ResponseBo getHelp(Integer infoId) {
        try {
            Help help = this.helpService.selectByKey(infoId);
            return ResponseBo.ok(help);
        } catch (Exception e) {
            log.error("获取帮助失败", e);
            return ResponseBo.error("获取帮助失败，请联系网站管理员！");
        }
    }

    @Log("获取帮助信息")
    @RequestMapping("help/list")
    @RequiresPermissions("help:list")
    @ResponseBody
    public Map<String, Object> helpList(QueryRequest request, Help help) {
        return super.selectByPageNumSize(request, () -> this.helpService.findAllHelps(help));
    }

    @Log("新增帮助")
    @RequiresPermissions("help:add")
    @RequestMapping("help/add")
    @ResponseBody
    public ResponseBo addHelp(Help help) {
        try {
            return this.helpService.addHelp(help);
        } catch (Exception e) {
            log.error("新增帮助失败", e);
            return ResponseBo.error("新增帮助失败，请联系网站管理员！");
        }
    }
    
    @Log("修改帮助")
    @RequiresPermissions("help:update")
    @RequestMapping("help/update")
    @ResponseBody
    public ResponseBo updateHelp(Help help) {
        try {
            return this.helpService.updateHelp(help);
        } catch (Exception e) {
            log.error("修改帮助失败", e);
            return ResponseBo.error("修改帮助失败，请联系网站管理员！");
        }
    }
    
    @Log("删除帮助")
    @RequiresPermissions("help:delete")
    @RequestMapping("help/delete")
    @ResponseBody
    public ResponseBo deleteHelp(String ids) {
        try {
        	return this.helpService.deleteHelps(ids);
        } catch (Exception e) {
            log.error("删除帮助失败", e);
            return ResponseBo.error("删除帮助失败，请联系网站管理员！");
        }
    }
    
}
