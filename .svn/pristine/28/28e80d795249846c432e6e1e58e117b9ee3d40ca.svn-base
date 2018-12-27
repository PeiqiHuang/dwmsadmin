package com.dwms.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.common.service.impl.BaseService;
import com.dwms.system.dao.ApplyMapper;
import com.dwms.system.domain.Apply;
import com.dwms.system.domain.Party;
import com.dwms.system.service.ApplyService;
import com.dwms.system.service.PartyService;

@Service("applyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ApplyServiceImpl extends BaseService<Apply> implements ApplyService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplyMapper applyMapper;
	@Autowired
	private PartyService partyService;

	@Override
	public List<Apply> findAllApplys(Apply apply) {
		try {
			Example example = new Example(Apply.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(apply.getPartyName())) {
				criteria.andCondition("partyName like ", "%" + apply.getPartyName() + "%");
			}
			if (null != apply.getStatus()) {
				criteria.andCondition("status = ", apply.getStatus());
			}
			example.setOrderByClause("createTime desc");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取党组织入驻列表失败", e);
			return new ArrayList<>();
		}

	}

	@Override
	public Apply findByName(String partyName) {
		Example example = new Example(Apply.class);
		example.createCriteria().andCondition("lower(partyName)=", partyName.toLowerCase());
		List<Apply> list = this.selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Apply findById(Integer applyId) {
		return this.selectByKey(applyId);
	}

	@Override
	@Transactional
	public void addParty(Integer applyId, Party party) {
		// 创建党支部
		party.setSource(Party.SOURCE_APP);// 入驻申请创建
		Integer partyId = partyService.addParty(party);
		// 修改入驻申请状态
		Apply apply = findById(applyId);
		apply.setStatus(Apply.STATUS_PASS);//审核通过
		apply.setPartyId(partyId);
		this.updateAll(apply);
	}

}
