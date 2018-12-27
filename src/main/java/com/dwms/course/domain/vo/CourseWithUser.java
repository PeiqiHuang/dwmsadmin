package com.dwms.course.domain.vo;

import java.util.List;

import lombok.Data;

import com.dwms.course.domain.Course;

@Data
public class CourseWithUser extends Course {

	// 参与党员
	private String userId;
	
	private List<String> userIds;
	
}
