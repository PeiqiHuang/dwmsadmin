package com.dwms.birthday.service.impl;

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

import com.dwms.birthday.dao.WishMapper;
import com.dwms.birthday.dao.WishTplMapper;
import com.dwms.birthday.domain.WishTpl;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.course.domain.Course;

@Service("wishTplService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class WishTplServiceImpl extends BaseService<WishTpl> implements WishTplService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WishMapper wishMapper;
    
    @Autowired
    private WishTplMapper wishTplMapper;

    @Override
    public List<WishTpl> findAll(WishTpl tpl) {
    	try {
			Example example = new Example(WishTpl.class);
			Criteria criteria = example.createCriteria();
			if (null != tpl.getPartyId()) {
				criteria.andCondition("partyId = ", tpl.getPartyId());
			}
			if (StringUtils.isNotBlank(tpl.getWishText())) {
				criteria.andCondition("wishText like ", "%" + tpl.getWishText() + "%");
			}
			example.setOrderByClause("createTime desc");
			List<WishTpl> tpls = this.selectByExample(example);
			return tpls;
		} catch (Exception e) {
			log.error("获取祝福语模板列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public ResponseBo addWishTpl(WishTpl tpl) {
        this.wishTplMapper.insertSelective(tpl);
        return ResponseBo.ok("新增祝福语模板成功！");
    }

	@Override
    @Transactional
    public ResponseBo updateWishTpl(WishTpl tpl) {
        this.updateNotNull(tpl);
        return ResponseBo.ok("修改祝福语模板成功！");
    }

	@Override
	@Transactional
	public ResponseBo deleteWishTpls(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		this.batchDelete(list, "tplId", WishTpl.class);
		return ResponseBo.ok("删除祝福语模板成功！");
	}

}
