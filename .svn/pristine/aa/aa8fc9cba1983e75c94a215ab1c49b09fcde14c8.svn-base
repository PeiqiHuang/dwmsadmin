package com.dwms.due.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.due.domain.Due;
import com.dwms.due.domain.vo.DueWithUser;

public interface DueService extends IService<Due> {

    List<Due> findAllDues(Due due);

    void addDue(Due due, String[] users);
    
    ResponseBo updateDue(Due due, String[] users);

    ResponseBo deleteDues(String ids);

	DueWithUser findById(Integer dueId);

}
