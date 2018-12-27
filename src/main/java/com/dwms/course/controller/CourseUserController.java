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
import com.dwms.common.util.FileUtils;
import com.dwms.course.domain.Course;
import com.dwms.course.domain.CourseUser;
import com.dwms.course.service.CourseService;
import com.dwms.course.service.CourseUserService;

@Controller
public class CourseUserController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseUserService courseUserService;
    
    @RequestMapping("courseUser")
    @RequiresPermissions("courseUser:list")
    public String index(Model model, Integer courseId) {
        model.addAttribute("courseId", courseId);
        return "course/courseUser/courseUser";
    }
    
    @Log("获取党课统计信息")
    @RequestMapping("courseUser/list")
    @RequiresPermissions("courseUser:list")
    @ResponseBody
    public Map<String, Object> courseUserList(QueryRequest request, CourseUser courseUser) {
        return super.selectByPageNumSize(request, () -> this.courseUserService.findAll(courseUser));
    }
    
    @RequestMapping("courseUser/excel")
    @ResponseBody
    public ResponseBo courseUserExcel(CourseUser courseUser) {
        try {
            Course course = this.courseService.selectByKey(courseUser.getCourseId());
            String sheetName = String.format("%s_课程统计表", course.getCourseName());
            List<CourseUser> list = this.courseUserService.findAll(courseUser);
            return FileUtils.createExcelByPOIKit(sheetName, list, CourseUser.class);
        } catch (Exception e) {
            log.error("导出课程统计Excel失败", e);
            return ResponseBo.error("导出Excel失败，请联系网站管理员！");
        }
    }

    @RequestMapping("courseUser/csv")
    @ResponseBody
    public ResponseBo courseUserCsv(CourseUser courseUser) {
        try {
            Course course = this.courseService.selectByKey(courseUser.getCourseId());
            String sheetName = String.format("%s_课程统计表", course.getCourseName());
            List<CourseUser> list = this.courseUserService.findAll(courseUser);
            return FileUtils.createCsv(sheetName, list, CourseUser.class);
        } catch (Exception e) {
            log.error("导出课程统计Csv失败", e);
            return ResponseBo.error("导出Csv失败，请联系网站管理员！");
        }
    }
}
