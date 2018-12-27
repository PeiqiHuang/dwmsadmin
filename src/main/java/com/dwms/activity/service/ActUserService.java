package com.dwms.activity.service;

import java.util.List;

import com.dwms.activity.domain.ActUser;
import com.dwms.common.service.IService;

public interface ActUserService extends IService<ActUser> {

    List<ActUser> findAllByActId(ActUser actUser);

}
