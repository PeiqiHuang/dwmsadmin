package com.dwms.due.dao;

import java.util.List;

import com.dwms.common.config.MyMapper;
import com.dwms.due.domain.Due;
import com.dwms.due.domain.vo.DueWithUser;

public interface DueMapper extends MyMapper<Due> {

    List<DueWithUser> findById(Integer dueId);
}