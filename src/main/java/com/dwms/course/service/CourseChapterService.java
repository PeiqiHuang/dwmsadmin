package com.dwms.course.service;

import java.util.List;

import com.dwms.common.domain.Tree;
import com.dwms.common.service.IService;
import com.dwms.course.domain.CourseChapter;

public interface CourseChapterService extends IService<CourseChapter> {

    Tree<CourseChapter> getChapterTree();
    
    List<CourseChapter> findAll(CourseChapter courseChapter);

    void addChapter(CourseChapter courseChapter);

    void updateChapter(CourseChapter courseChapter);

    void deleteChapters(String ids);

    //重新计算所有章和课程的时长
    void updateCourseAndAllChapters(Integer courseId);

}
