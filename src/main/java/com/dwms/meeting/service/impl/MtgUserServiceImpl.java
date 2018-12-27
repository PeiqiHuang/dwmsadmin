package com.dwms.meeting.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dwms.common.service.impl.BaseService;
import com.dwms.meeting.dao.MtgUserMapper;
import com.dwms.meeting.domain.MtgUser;
import com.dwms.meeting.service.MtgUserService;

@Service("mtgUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MtgUserServiceImpl extends BaseService<MtgUser> implements MtgUserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MtgUserMapper mtgUserMapper;

	@Override
	public List<MtgUser> findAllByMtgId(MtgUser mtgUser) {
		return mtgUserMapper.findAllByMtgId(mtgUser);
	}

}
