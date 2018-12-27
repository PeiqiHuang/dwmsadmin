package com.dwms.system.service.impl;

import java.util.ArrayList;
import java.util.Date;
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

import com.dwms.common.domain.Tree;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.TreeUtils;
import com.dwms.system.dao.PartyMapper;
import com.dwms.system.domain.Party;
import com.dwms.system.service.PartyService;

@Service("partyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PartyServiceImpl extends BaseService<Party> implements PartyService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private PartyMapper partyMapper;

	@Override
	public Tree<Party> getPartyTree() {
		List<Tree<Party>> trees = new ArrayList<>();
		List<Party> partys = this.findAllPartys(new Party());
		partys.forEach(party -> {
			Tree<Party> tree = new Tree<>();
			tree.setId(party.getPartyId().toString());
			tree.setParentId(party.getParentId().toString());
			tree.setText(party.getPartyName());
			trees.add(tree);
		});
		return TreeUtils.build(trees);
	}

	@Override
	public List<Party> findAllPartys(Party party) {
		try {
			Example example = new Example(Party.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(party.getPartyName())) {
				criteria.andCondition("partyName like ", "%" + party.getPartyName() + "%");
			}
			if (null != party.getSource()) {
				criteria.andCondition("source = ", party.getSource());
			}
			if (null != party.getStatus()) {
				criteria.andCondition("status = ", party.getStatus());
			}
			example.setOrderByClause("partyId");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取党支部列表失败", e);
			return new ArrayList<>();
		}

	}

	@Override
	public Party findByName(String partyName) {
		Example example = new Example(Party.class);
		example.createCriteria().andCondition("lower(partyName)=", partyName.toLowerCase());
		List<Party> list = this.selectByExample(example);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	@Transactional
	public Integer addParty(Party party) {
		Integer parentId = party.getParentId();
		if (parentId == null)
			party.setParentId(0);
		if (party.getSource() == null) {
			party.setSource(Party.SOURCE_ADMIN);// 默认后台创建
		}
		party.setStatus(Party.STATUS_VALID);// 状态默认正常
		//party.setCreateTime(new Date());
		//this.save(party);
		this.partyMapper.insertSelective(party);
		return party.getPartyId();
	}

	/*@Override
	@Transactional
	public void deletePartys(String partyIds) {
		List<String> list = Arrays.asList(partyIds.split(","));
		this.batchDelete(list, "partyId", Party.class);
		this.partyMapper.changeToTop(list);
	}*/

	@Override
	public Party findById(Integer partyId) {
		return this.selectByKey(partyId);
	}

	@Override
	@Transactional
	public void updateParty(Party party) {
		this.updateNotNull(party);
	}
	
	@Override
	public List<Party> findAllPartys() {
		try {
			Example example = new Example(Party.class);
			example.createCriteria().andCondition("status = ", Party.STATUS_VALID);
			example.setOrderByClause("partyId");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取有效的党支部列表失败", e);
			return new ArrayList<>();
		}

	}
}
