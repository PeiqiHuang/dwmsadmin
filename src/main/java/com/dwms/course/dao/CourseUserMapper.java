package com.dwms.course.dao;

import java.util.List;

import com.dwms.common.config.MyMapper;
import com.dwms.course.domain.CourseUser;

public interface CourseUserMapper extends MyMapper<CourseUser> {

    List<CourseUser> findAll(CourseUser courseUser);
}