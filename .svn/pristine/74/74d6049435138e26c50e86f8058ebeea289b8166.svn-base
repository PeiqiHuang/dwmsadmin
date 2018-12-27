package com.dwms.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dwms.common.annotation.Log;
import com.dwms.common.controller.BaseController;
import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.util.FileUtils;
import com.dwms.system.domain.Apply;
import com.dwms.system.domain.Party;
import com.dwms.system.service.ApplyService;

@Controller
public class ApplyController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplyService applyService;

    @Log("获取党组织入驻信息")
    @RequestMapping("apply")
    @RequiresPermissions("apply:list")
    public String index() {
        return "system/apply/apply";
    }

    @RequestMapping("apply/getApply")
    @ResponseBody
    public ResponseBo getApply(Integer applyId) {
        try {
            Apply apply = this.applyService.findById(applyId);
            return ResponseBo.ok(apply);
        } catch (Exception e) {
            log.error("获取党组织入驻信息失败", e);
            return ResponseBo.error("获取党组织入驻信息失败，请联系网站管理员！");
        }
    }

    @RequestMapping("apply/list")
    @RequiresPermissions("apply:list")
    @ResponseBody
    public Map<String, Object> list(QueryRequest request, Apply apply) {
    	return super.selectByPageNumSize(request, () -> this.applyService.findAllApplys(apply));
    }

    @RequestMapping("apply/excel")
    @ResponseBody
    public ResponseBo applyExcel(Apply apply) {
        try {
            List<Apply> list = this.applyService.findAllApplys(apply);
            return FileUtils.createExcelByPOIKit("党组织入驻表", list, Apply.class);
        } catch (Exception e) {
            log.error("导出党组织入驻信息Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("apply/csv")
    @ResponseBody
    public ResponseBo applyCsv(Apply apply) {
        try {
            List<Apply> list = this.applyService.findAllApplys(apply);
            return FileUtils.createCsv("党组织入驻表", list, Apply.class);
        } catch (Exception e) {
            log.error("获取党组织入驻信息Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }

    @Log("通过入驻申请 ")
    @RequiresPermissions("apply:pass")
    @RequestMapping("apply/pass")
    @ResponseBody
    public ResponseBo pass(Integer applyId, Party party) {
        try {
            this.applyService.addParty(applyId, party);
            return ResponseBo.ok("新增党支部成功！");
        } catch (Exception e) {
            log.error("新增党支部失败", e);
            return ResponseBo.error("新增党支部失败，请联系网站管理员！");
        }
    }
}
