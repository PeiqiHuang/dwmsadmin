package com.dwms.due.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.due.domain.DueUser;

public interface DueUserService extends IService<DueUser> {

    List<DueUser> findAllByDueId(DueUser dueUser);

    ResponseBo confirm(Integer duId);

}
