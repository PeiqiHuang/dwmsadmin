package com.dwms.advice.dao;

import java.util.List;

import com.dwms.advice.domain.Advice;
import com.dwms.common.config.MyMapper;

public interface AdviceMapper extends MyMapper<Advice> {
    
    List<Advice> findAllAdvices(Advice advice);
}