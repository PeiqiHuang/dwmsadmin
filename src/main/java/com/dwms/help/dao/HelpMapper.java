package com.dwms.help.dao;

import java.util.List;

import com.dwms.common.config.MyMapper;
import com.dwms.help.domain.Help;

public interface HelpMapper extends MyMapper<Help> {
    
    List<Help> findAllHelps(Help help);
}