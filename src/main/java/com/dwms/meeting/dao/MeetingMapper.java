package com.dwms.meeting.dao;

import java.util.List;

import com.dwms.common.config.MyMapper;
import com.dwms.meeting.domain.Meeting;
import com.dwms.meeting.domain.vo.MeetingWithUser;

public interface MeetingMapper extends MyMapper<Meeting> {
	
	List<MeetingWithUser> findById(Integer mtgId);
}