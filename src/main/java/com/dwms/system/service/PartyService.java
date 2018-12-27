package com.dwms.system.service;

import java.util.List;

import com.dwms.common.domain.QueryRequest;
import com.dwms.common.domain.Tree;
import com.dwms.common.service.IService;
import com.dwms.system.domain.Party;

public interface PartyService extends IService<Party> {

	Tree<Party> getPartyTree();

	List<Party> findAllPartys(Party party);

	Party findByName(String partyName);

	Party findById(Integer partyId);
	
	/**
	 * 返回插入的主键
	 */
	Integer addParty(Party party);
	
	void updateParty(Party party);

	List<Party> findAllPartys();

	//void deletePartys(String partyIds);
}
