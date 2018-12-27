package com.dwms.examine.domain.vo;

import java.util.List;

import com.dwms.examine.domain.Exam;

public class ExamWithUser extends Exam {

	// 参与党员
	private String userId;
	
	private List<String> userIds;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}
	
}
