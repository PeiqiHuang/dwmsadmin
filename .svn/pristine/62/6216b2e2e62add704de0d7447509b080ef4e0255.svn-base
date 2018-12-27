package com.dwms.advice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dwms.advice.dao.AdviceMapper;
import com.dwms.advice.domain.Advice;
import com.dwms.advice.service.AdviceService;
import com.dwms.common.service.impl.BaseService;

@Service("adviceService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdviceServiceImpl extends BaseService<Advice> implements AdviceService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AdviceMapper adviceMapper;
    
    @Override
    public List<Advice> findAllAdvices(Advice advice) {
    	try {
			return this.adviceMapper.findAllAdvices(advice);
		} catch (Exception e) {
			log.error("获取反馈列表失败", e);
			return new ArrayList<>();
		}
    }
}
