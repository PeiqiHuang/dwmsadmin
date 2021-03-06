package com.dwms.course.controller;

import java.util.ArrayList;
import java.util.List;

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
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.domain.Tree;
import com.dwms.course.domain.Course;
import com.dwms.course.domain.CourseChapter;
import com.dwms.course.service.CourseChapterService;
import com.dwms.course.service.CourseService;
import com.dwms.system.domain.Party;

@Controller
public class CourseChapterController extends BaseController {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseChapterService courseChapterService;
    
    @RequestMapping("courseChapter")
    @RequiresPermissions("courseChapter:list")
    public String index(Model model, Integer courseId) {
        Course course = courseService.selectByKey(courseId);
        model.addAttribute("course", course);
        return "course/chapter/chapter";
    }
    
    @Log("获取党课章节信息")
    @RequestMapping("courseChapter/list")
    @RequiresPermissions("courseChapter:list")
    @ResponseBody
    public List<CourseChapter> courseChapterList(CourseChapter courseChapter) {
        try {
            return this.courseChapterService.findAll(courseChapter);
        } catch (Exception e) {
            log.error("获取党课章节列表失败", e);
            return new ArrayList<>();
        }
    }
    
    @RequestMapping("courseChapter/tree")
    @ResponseBody
    public ResponseBo getCourseChapterTree() {
        try {
            Tree<CourseChapter> tree = this.courseChapterService.getChapterTree();
            return ResponseBo.ok(tree);
        } catch (Exception e) {
            log.error("获取党课章节树失败", e);
            return ResponseBo.error("获取党课章节树失败！");
        }
    }
    
    @RequestMapping("courseChapter/getChapter")
    @ResponseBody
    public ResponseBo getChapter(Integer chapterId) {
        try {
            CourseChapter chapter = this.courseChapterService.selectByKey(chapterId);
            return ResponseBo.ok(chapter);
        } catch (Exception e) {
            log.error("获取章节信息失败", e);
            return ResponseBo.error("获取章节信息失败，请联系网站管理员！");
        }
    }
    
    @Log("新增党课章节 ")
    @RequiresPermissions("courseChapter:add")
    @RequestMapping("courseChapter/add")
    @ResponseBody
    public ResponseBo add(CourseChapter courseChapter) {
        try {
            this.courseChapterService.addChapter(courseChapter);
            return ResponseBo.ok("新增党课章节成功！");
        } catch (Exception e) {
            log.error("新增党课章节失败", e);
            return ResponseBo.error("新增党课章节失败，请联系网站管理员！");
        }
    }
    
    @Log("修改党课章节 ")
    @RequiresPermissions("courseChapter:update")
    @RequestMapping("courseChapter/update")
    @ResponseBody
    public ResponseBo update(CourseChapter courseChapter) {
        try {
            this.courseChapterService.updateChapter(courseChapter);
            return ResponseBo.ok("修改党课章节成功！");
        } catch (Exception e) {
            log.error("修改党课章节失败", e);
            return ResponseBo.error("修改党课章节失败，请联系网站管理员！");
        }
    }
    
    @Log("删除章节")
    @RequiresPermissions("courseChapter:delete")
    @RequestMapping("courseChapter/delete")
    @ResponseBody
    public ResponseBo deleteChapters(String ids) {
        try {
            this.courseChapterService.deleteChapters(ids);
            return ResponseBo.ok("删除章节成功！");
        } catch (Exception e) {
            log.error("删除章节失败", e);
            return ResponseBo.error("删除章节失败，请联系网站管理员！");
        }
    }
}
