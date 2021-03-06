package com.dwms.course.dao;

import java.util.List;

import com.dwms.common.config.MyMapper;
import com.dwms.course.domain.Course;
import com.dwms.course.domain.vo.CourseWithUser;

public interface CourseMapper extends MyMapper<Course> {

	List<CourseWithUser> findById(Integer courseId);
	
	List<Course> findAllCourses(Course course);
}