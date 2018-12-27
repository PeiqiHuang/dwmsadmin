package com.dwms.course.controller;

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
import com.dwms.course.domain.CourseCat;
import com.dwms.course.service.CourseCatService;
import com.dwms.course.service.CourseService;
import com.google.common.collect.Lists;

@Controller
public class CourseCatController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private CourseCatService courseCatService;
    
    @RequestMapping("courseCat")
    @RequiresPermissions("courseCat:list")
    public String index(Model model) {
        model.addAttribute("isAdmin", isAdmin());
        return "course/category/courseCat";
    }
    
    @Log("获取党课分类信息")
    @RequestMapping("courseCat/list")
    @RequiresPermissions("courseCat:list")
    @ResponseBody
    public Map<String, Object> courseCatList(QueryRequest request, CourseCat courseCat) {
        if (!isAdmin()) {
            courseCat.setPartyId(getPartyId());
        }
        return super.selectByPageNumSize(request, () -> this.courseCatService.findAll(courseCat));
    }
    
    @RequestMapping("courseCat/getCategorys")
    @ResponseBody
    public ResponseBo getCategorys(Integer partyId) {
        if (null == partyId) {
            if (isAdmin()) {
                return ResponseBo.ok(Lists.newArrayList());
            } else {
                partyId = getPartyId();
            }
        }
        try {
            CourseCat cat = new CourseCat();
            cat.setPartyId(partyId);
            List<CourseCat> cats = this.courseCatService.findAll(cat);
            return ResponseBo.ok(cats);
        } catch (Exception e) {
            log.error("获取党课分类失败", e);
            return ResponseBo.error("获取党课分类失败，请联系网站管理员！");
        }
    }
    
    @RequestMapping("courseCat/getCourseCat")
    @ResponseBody
    public ResponseBo getCourseCat(Integer categoryId) {
        try {
            CourseCat courseCat = this.courseCatService.selectByKey(categoryId);
            return ResponseBo.ok(courseCat);
        } catch (Exception e) {
            log.error("获取党课分类失败", e);
            return ResponseBo.error("获取党课分类失败，请联系网站管理员！");
        }
    }
    
    @Log("新增党课分类")
    @RequiresPermissions("courseCat:add")
    @RequestMapping("courseCat/add")
    @ResponseBody
    public ResponseBo addCourseCat(CourseCat courseCat) {
        if (null == courseCat.getPartyId()) {
            courseCat.setPartyId(getPartyId());
        }
        try {
            return this.courseCatService.addCourseCat(courseCat);
//            return ResponseBo.ok("新增党课分类成功！");
        } catch (Exception e) {
            log.error("新增党课分类失败", e);
            return ResponseBo.error("新增党课分类失败，请联系网站管理员！");
        }
    }

    @Log("修改党课分类")
    @RequiresPermissions("courseCat:update")
    @RequestMapping("courseCat/update")
    @ResponseBody
    public ResponseBo updateCourseCat(CourseCat courseCat) {
        try {
            return this.courseCatService.updateCourseCat(courseCat);
            //return ResponseBo.ok("修改党课分类成功！");
        } catch (Exception e) {
            log.error("修改党课分类失败", e);
            return ResponseBo.error("修改党课分类失败，请联系网站管理员！");
        }
    }
    
    @Log("删除党课分类")
    @RequiresPermissions("courseCat:delete")
    @RequestMapping("courseCat/delete")
    @ResponseBody
    public ResponseBo deleteSum(String ids) {
        try {
        	return this.courseCatService.deleteCourseCats(ids);
        } catch (Exception e) {
            log.error("删除党课分类失败", e);
            return ResponseBo.error("删除党课分类失败，请联系网站管理员！");
        }
    }

}
