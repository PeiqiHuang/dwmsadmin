package com.dwms.meeting.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.meeting.domain.Meeting;
import com.dwms.meeting.domain.vo.MeetingWithUser;

public interface MeetingService extends IService<Meeting> {

    List<Meeting> findAllMeetings(Meeting meeting);

    void addMeeting(Meeting meeting, String[] users);

    void updateMeeting(Meeting meeting, String[] users);

    ResponseBo deleteMeetings(String ids);

	MeetingWithUser findById(Integer mtgId);

}
