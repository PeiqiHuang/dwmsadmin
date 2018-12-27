package com.dwms.activity.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dwms.activity.dao.ActUserMapper;
import com.dwms.activity.domain.ActUser;
import com.dwms.activity.service.ActUserService;
import com.dwms.common.service.impl.BaseService;

@Service("actUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ActUserServiceImpl extends BaseService<ActUser> implements ActUserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ActUserMapper actUserMapper;

    @Override
    public List<ActUser> findAllByActId(ActUser actUser) {
        return actUserMapper.findAllByActId(actUser);
    }
}
