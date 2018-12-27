package com.dwms.due.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.due.domain.DueAccount;

public interface DueAccountService extends IService<DueAccount> {

    List<DueAccount> findAll(DueAccount dueAccount);

    ResponseBo addAccount(DueAccount dueAccount);
    
    ResponseBo updateAccount(DueAccount dueAccount);
    
    ResponseBo deleteAccounts(String ids);

    DueAccount findById(Integer accId);
}
