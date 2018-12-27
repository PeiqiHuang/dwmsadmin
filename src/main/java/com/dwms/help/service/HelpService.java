package com.dwms.help.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.help.domain.Help;

public interface HelpService extends IService<Help> {

    List<Help> findAllHelps(Help help);

    ResponseBo addHelp(Help help);
    
    ResponseBo updateHelp(Help help);

    ResponseBo deleteHelps(String ids);

}
