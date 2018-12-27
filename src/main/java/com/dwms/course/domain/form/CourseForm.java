package com.dwms.course.domain.form;

import java.util.List;

import lombok.Data;

import org.springframework.web.multipart.MultipartFile;

import com.dwms.course.domain.Course;

@Data
public class CourseForm extends Course {

	private String[] users;
	
	private MultipartFile[] files;
	
}
