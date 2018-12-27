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
import com.dwms.help.domain.HelpType;
import com.dwms.help.service.HelpTypeService;

@Controller
public class HelpTypeController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HelpTypeService helpTypeService;
    
    @RequestMapping("helpType")
    @RequiresPermissions("help:type")
    public String index(Model model) {
        model.addAttribute("isAdmin", isAdmin());
        return "help/type/helpType";
    }
    
    @Log("获取帮助类型信息")
    @RequestMapping("helpType/list")
    @RequiresPermissions("help:type")
    @ResponseBody
    public Map<String, Object> helpTypeList(QueryRequest request, HelpType helpType) {
        return super.selectByPageNumSize(request, () -> this.helpTypeService.findAll(helpType));
    }
    
    @RequestMapping("helpType/getTypes")
    @ResponseBody
    public ResponseBo getTypes() {
        try {
            HelpType type = new HelpType();
            type.setTypeStatus(HelpType.STATUS_VALID);
            return ResponseBo.ok(this.helpTypeService.findAll(type));
        } catch (Exception e) {
            log.error("获取帮助类型失败", e);
            return ResponseBo.error("获取帮助类型失败，请联系网站管理员！");
        }
    }
    
    @RequestMapping("helpType/getType")
    @ResponseBody
    public ResponseBo getType(Integer typeId) {
        try {
            HelpType helpType = this.helpTypeService.selectByKey(typeId);
            return ResponseBo.ok(helpType);
        } catch (Exception e) {
            log.error("获取帮助类型失败", e);
            return ResponseBo.error("获取帮助类型失败，请联系网站管理员！");
        }
    }
    
    @Log("新增帮助类型")
    @RequiresPermissions("help:type")
    @RequestMapping("helpType/add")
    @ResponseBody
    public ResponseBo addHelpType(HelpType helpType) {
        try {
            return this.helpTypeService.addHelpType(helpType);
        } catch (Exception e) {
            log.error("新增帮助类型失败", e);
            return ResponseBo.error("新增帮助类型失败，请联系网站管理员！");
        }
    }

    @Log("修改帮助类型")
    @RequiresPermissions("help:type")
    @RequestMapping("helpType/update")
    @ResponseBody
    public ResponseBo updateHelpType(HelpType helpType) {
        try {
            return this.helpTypeService.updateHelpType(helpType);
        } catch (Exception e) {
            log.error("修改帮助类型失败", e);
            return ResponseBo.error("修改帮助类型失败，请联系网站管理员！");
        }
    }
    
    @Log("删除帮助类型")
    @RequiresPermissions("help:type")
    @RequestMapping("helpType/delete")
    @ResponseBody
    public ResponseBo deleteSum(String ids) {
        try {
        	return this.helpTypeService.deleteHelpTypes(ids);
        } catch (Exception e) {
            log.error("删除帮助类型失败", e);
            return ResponseBo.error("删除帮助类型失败，请联系网站管理员！");
        }
    }

}
