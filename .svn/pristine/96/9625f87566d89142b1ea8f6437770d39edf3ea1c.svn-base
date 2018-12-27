package com.dwms.course.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.course.domain.Course;
import com.dwms.course.domain.form.CourseForm;
import com.dwms.course.domain.vo.CourseWithUser;

public interface CourseService extends IService<Course> {

    List<Course> findAllCourses(Course course);

    ResponseBo addCourse(CourseForm form);

    ResponseBo updateCourse(CourseForm form);

    ResponseBo deleteCourses(String ids);

	CourseWithUser findById(Integer courseId);

}
