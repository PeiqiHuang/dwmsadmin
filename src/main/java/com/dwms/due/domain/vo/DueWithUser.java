package com.dwms.due.domain.vo;

import java.util.List;

import lombok.Data;

import com.dwms.due.domain.Due;

@Data
public class DueWithUser extends Due {

	// 参与党员
	private String userId;
	
	private List<String> userIds;
	
}
