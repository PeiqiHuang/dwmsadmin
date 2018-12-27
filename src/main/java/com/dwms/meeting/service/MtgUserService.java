package com.dwms.meeting.service;

import java.util.List;

import com.dwms.common.service.IService;
import com.dwms.meeting.domain.MtgUser;

public interface MtgUserService extends IService<MtgUser> {

    List<MtgUser> findAllByMtgId(MtgUser mtgUser);

}
