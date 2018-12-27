package com.dwms.meeting.domain.vo;

import java.util.List;

import com.dwms.meeting.domain.Meeting;

public class MeetingWithUser extends Meeting {

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
