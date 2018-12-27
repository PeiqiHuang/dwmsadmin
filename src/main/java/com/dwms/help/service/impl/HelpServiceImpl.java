package com.dwms.help.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.help.dao.HelpMapper;
import com.dwms.help.domain.Help;
import com.dwms.help.service.HelpService;

@Service("helpService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HelpServiceImpl extends BaseService<Help> implements HelpService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HelpMapper helpMapper;
    
    @Override
    public List<Help> findAllHelps(Help help) {
    	try {
			return this.helpMapper.findAllHelps(help);
		} catch (Exception e) {
			log.error("获取帮助列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public ResponseBo addHelp(Help help) {
        this.helpMapper.insertSelective(help);
        return ResponseBo.ok("新增帮助成功！");
    }

	@Override
    @Transactional
    public ResponseBo updateHelp(Help help) {
        this.updateNotNull(help);

        return ResponseBo.ok("修改帮助成功！");
    }

	@Override
	@Transactional
	public ResponseBo deleteHelps(String ids) {
	    List<String> list = Arrays.asList(ids.split(","));
	    this.batchDelete(list, "infoId", Help.class);
        return ResponseBo.ok("删除帮助成功！");
	}

}
