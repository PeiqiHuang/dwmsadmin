package com.dwms.due.service.impl;

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
import com.dwms.course.domain.Course;
import com.dwms.due.dao.DueMapper;
import com.dwms.due.dao.DueUserMapper;
import com.dwms.due.domain.Due;
import com.dwms.due.domain.DueUser;
import com.dwms.due.domain.vo.DueWithUser;
import com.dwms.due.service.DueService;
import com.google.common.collect.Lists;

@Service("dueService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DueServiceImpl extends BaseService<Due> implements DueService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DueMapper dueMapper;
    
    @Autowired
    private DueUserMapper dueUserMapper;
    
    @Override
    public List<Due> findAllDues(Due due) {
    	try {
			Example example = new Example(Due.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("status != ", Due.STATUS_DELETE);//不显示状态为删除的
			if (null != due.getPartyId()) {
				criteria.andCondition("partyId = ", due.getPartyId());
			}
			if (StringUtils.isNotBlank(due.getTitle())) {
				criteria.andCondition("title like ", "%" + due.getTitle() + "%");
			}
			if (null != due.getDueItem()) {
			    criteria.andCondition("dueItem = ", due.getDueItem());
			}
			if (null != due.getDueType()) {
			    criteria.andCondition("dueType = ", due.getDueType());
			}
			if (null != due.getStatus()) {
				criteria.andCondition("status = ", due.getStatus());
			}
			if (StringUtils.isNotBlank(due.getTimeField())) {
                String[] timeArr = due.getTimeField().split("~");
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') <=", timeArr[1]);
            }
			example.setOrderByClause("status desc, createTime desc");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取缴费项目列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public void addDue(Due due, String[] users) {
        //due.setStatus(Due.STATUS_WAIT);
        this.dueMapper.insertSelective(due);
        //插入参与用户
        addDueUser(due, Arrays.asList(users));
    }
    

    private void addDueUser(Due due, List<String> users) {
    	for(String userId :users) {
    		DueUser dueUser = new DueUser();
    		dueUser.setDueId(due.getDueId());
    		dueUser.setUserId(userId);
    		this.dueUserMapper.insertSelective(dueUser);
    	}
	}

	@Override
    @Transactional
    public ResponseBo updateDue(Due due, String[] users) {
	    boolean noEdit = (null == due.getStatus());//发布后修改为null
	    if (!noEdit && due.getStatus() == Due.STATUS_VALID) {
	        due.setReleaseTime(new Date());
        }
        this.updateNotNull(due);
        if (!noEdit) {
            //插入新的，删除提出的用户
            updateMsgUser(due, users);
        }
        return ResponseBo.ok("修改缴费项目成功！");
    }

	private void updateMsgUser(Due due, String[] users) {
		Example example = new Example(DueUser.class);
		example.createCriteria().andCondition("dueId=", due.getDueId());
		List<DueUser> duList = this.dueUserMapper.selectByExample(example);
		
		List<String> duUserList = Lists.newArrayList();
		for (DueUser mu : duList) {
			duUserList.add(mu.getUserId());
		}
		List<String> userList = Arrays.asList(users);
		// subtract(a,b) 截取a有的b没有的
		List<String> delUsers = ListUtils.subtract(duUserList, userList);
		List<String> addUsers = ListUtils.subtract(userList, duUserList);
		//delete
		if (!delUsers.isEmpty()) {
			example = new Example(DueUser.class);
			example.createCriteria().andCondition("dueId=", due.getDueId()).andIn("userId", delUsers);
			this.dueUserMapper.deleteByExample(example);
		}
		//insert
		addDueUser(due, addUsers);
	}

	@Override
	@Transactional
	public ResponseBo deleteDues(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		for (String id : list) {
			Due due = selectByKey(NumberUtils.toInt(id));
			due.setStatus(Due.STATUS_DELETE);
			this.updateNotNull(due);
		}
		return ResponseBo.ok("删除缴费项目成功！");
	}

	@Override
	public DueWithUser findById(Integer dueId) {
		List<DueWithUser> list = this.dueMapper.findById(dueId);
		if (list.isEmpty()) return null;
		List<String> users = Lists.newArrayList();
		for (DueWithUser mtg : list) {
			users.add(mtg.getUserId());
		}
		DueWithUser vo = list.get(0);
		vo.setUserIds(users);
		
		return vo;
	}
	
}
