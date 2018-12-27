package com.dwms.activity.dao;

import java.util.List;

import com.dwms.activity.domain.ActUser;
import com.dwms.common.config.MyMapper;

public interface ActUserMapper extends MyMapper<ActUser> {

    List<ActUser> findAllByActId(ActUser actUser);
}