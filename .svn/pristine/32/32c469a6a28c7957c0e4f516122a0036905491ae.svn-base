package com.dwms.help.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.help.dao.HelpMapper;
import com.dwms.help.dao.HelpTypeMapper;
import com.dwms.help.domain.Help;
import com.dwms.help.domain.HelpType;
import com.dwms.help.service.HelpTypeService;

@Service("helpTypeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class HelpTypeServiceImpl extends BaseService<HelpType> implements HelpTypeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HelpMapper helpMapper;
    
    @Autowired
    private HelpTypeMapper helpTypeMapper;

    @Override
    public List<HelpType> findAll(HelpType type) {
    	try {
			Example example = new Example(HelpType.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(type.getTypeName())) {
				criteria.andCondition("typeName like ", "%" + type.getTypeName() + "%");
			}
			if (null != type.getTypeStatus()) {
				criteria.andCondition("typeStatus = ", type.getTypeStatus());
			}
			example.setOrderByClause("typeStatus desc, createTime asc");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取帮助类型列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public ResponseBo addHelpType(HelpType type) {
        this.helpTypeMapper.insertSelective(type);
        return ResponseBo.ok("新增帮助类型成功！");
    }

	@Override
    @Transactional
    public ResponseBo updateHelpType(HelpType type) {
        this.updateNotNull(type);
        return ResponseBo.ok("修改帮助类型成功！");
    }

	@Override
	@Transactional
	public ResponseBo deleteHelpTypes(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		for (String id : list) {
			HelpType type = selectByKey(NumberUtils.toInt(id));
			Example example = new Example(HelpType.class);
			example.createCriteria().andCondition("infoType = ", id);
            List<Help> helps = this.helpMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(helps))
                return ResponseBo.error(String.format("删除帮助类型失败，有帮助已经选择了类型'%s'！", type.getTypeName())); 
		}
		this.batchDelete(list, "typeId", HelpType.class);
		return ResponseBo.ok("删除帮助类型成功！");
	}

}
