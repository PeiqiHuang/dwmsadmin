package com.dwms.system.service;

import java.util.List;

import com.dwms.common.service.IService;
import com.dwms.system.domain.Apply;
import com.dwms.system.domain.Party;

public interface ApplyService extends IService<Apply> {

	List<Apply> findAllApplys(Apply apply);

	Apply findByName(String partyName);

	Apply findById(Integer applyId);

	void addParty(Integer applyId, Party party);
}
