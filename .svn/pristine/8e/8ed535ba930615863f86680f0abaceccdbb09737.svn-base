package com.dwms.meeting.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.DateUtil;
import com.dwms.meeting.dao.MeetingMapper;
import com.dwms.meeting.dao.MtgUserMapper;
import com.dwms.meeting.domain.Meeting;
import com.dwms.meeting.domain.MtgUser;
import com.dwms.meeting.domain.vo.MeetingWithUser;
import com.dwms.meeting.service.MeetingService;
import com.google.common.collect.Lists;

@Service("meetingService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MeetingServiceImpl extends BaseService<Meeting> implements MeetingService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MeetingMapper meetingMapper;
    
    @Autowired
    private MtgUserMapper mtgUserMapper;

    @Override
    public List<Meeting> findAllMeetings(Meeting meeting) {
    	try {
			Example example = new Example(Meeting.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("status != ", Meeting.STATUS_DELETE);//不显示状态为删除的
			if (null != meeting.getPartyId()) {
				criteria.andCondition("partyId = ", meeting.getPartyId());
			}
			if (StringUtils.isNotBlank(meeting.getMtgName())) {
				criteria.andCondition("mtgName like ", "%" + meeting.getMtgName() + "%");
			}
			if (null != meeting.getStatus()) {
				criteria.andCondition("status = ", meeting.getStatus());
			}
			if (StringUtils.isNotBlank(meeting.getTimeField())) {
                String[] timeArr = meeting.getTimeField().split("~");
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') <=", timeArr[1]);
            }
			example.setOrderByClause("status desc, createTime desc");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取会议列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public void addMeeting(Meeting meeting, String[] users) {
        this.meetingMapper.insertSelective(meeting);
        //插入参与用户
        addMtgUser(meeting, Arrays.asList(users));
    }

    private void addMtgUser(Meeting meeting, List<String> users) {
    	for(String userId :users) {
    		MtgUser mtgUser = new MtgUser();
    		mtgUser.setMtgId(meeting.getMtgId());
    		mtgUser.setUserId(userId);
    		this.mtgUserMapper.insertSelective(mtgUser);
    	}
	}

	@Override
    @Transactional
    public void updateMeeting(Meeting meeting, String[] users) {
        this.updateNotNull(meeting);
        //插入新的，删除提出的用户
        updateMsgUser(meeting, users);
    }

	private void updateMsgUser(Meeting meeting, String[] users) {
		Example example = new Example(Meeting.class);
		example.createCriteria().andCondition("mtgId=", meeting.getMtgId());
		List<MtgUser> muList = this.mtgUserMapper.selectByExample(example);
		
		List<String> muUserList = Lists.newArrayList();
		for (MtgUser mu : muList) {
			muUserList.add(mu.getUserId());
		}
		List<String> userList = Arrays.asList(users);
		// subtract(a,b) 截取a有的b没有的
		List<String> delUsers = ListUtils.subtract(muUserList, userList);
		List<String> addUsers = ListUtils.subtract(userList, muUserList);
		//delete
		if (!delUsers.isEmpty()) {
			example = new Example(MtgUser.class);
			example.createCriteria().andCondition("mtgId=", meeting.getMtgId()).andIn("userId", delUsers);
			this.mtgUserMapper.deleteByExample(example);
		}
		//insert
		addMtgUser(meeting, addUsers);
	}

	@Override
	@Transactional
	public ResponseBo deleteMeetings(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		List<Meeting> mtgList = Lists.newArrayList();
		for (String id : list) {
			Meeting meeting = selectByKey(NumberUtils.toInt(id));
			int status = meeting.getStatus();
			boolean delete = false;
			if (status == Meeting.STATUS_VALID) {
				if(DateUtil.compareDate(new Date(), meeting.getEndTime()) > 0) {//已结束
					delete = true;
				}
			} else if (status == Meeting.STATUS_CANCEL
					||status == Meeting.STATUS_WAIT) {
				delete = true;
			}
			if (delete) {
				meeting.setStatus(Meeting.STATUS_DELETE);
				mtgList.add(meeting);
			} else {
				return ResponseBo.error("删除会议失败，所选的会议只能是已取消、待发布或已过期的！"); 
			}
		}
		for(Meeting mtg : mtgList) {
			this.updateNotNull(mtg);
		}
		return ResponseBo.ok("删除会议成功！");
	}

	@Override
	public MeetingWithUser findById(Integer mtgId) {
		List<MeetingWithUser> list = this.meetingMapper.findById(mtgId);
		if (list.isEmpty()) return null;
		List<String> users = Lists.newArrayList();
		for (MeetingWithUser mtg : list) {
			users.add(mtg.getUserId());
		}
		MeetingWithUser vo = list.get(0);
		vo.setUserIds(users);
		
		// progress set
		Date now = new Date();
		int progress = Meeting.PROGRESS_NOT_BEGIN;
		if (DateUtil.compareDate(vo.getBeginTime(), now) <= 0) {
			progress = Meeting.PROGRESS_BEGIN;
		}
		if (DateUtil.compareDate(vo.getEndTime(), now) <= 0) {
			progress = Meeting.PROGRESS_END;
		}
		vo.setProgress(progress);
		
		return vo;
	}
}
