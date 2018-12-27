package com.dwms.activity.domain.vo;

import java.util.List;

import lombok.Data;

import com.dwms.activity.domain.Activity;

@Data
public class ActivityWithUser extends Activity {

	// 参与党员
	private String userId;
	
	private List<String> userIds;
	
}
