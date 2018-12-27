package com.dwms.advert.service.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.advert.dao.AdvertKeyMapper;
import com.dwms.advert.dao.AdvertMapper;
import com.dwms.advert.domain.Advert;
import com.dwms.advert.domain.AdvertKey;
import com.dwms.advert.service.AdvertKeyService;
import com.dwms.common.config.ImageConfig;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;

@Service("dueAdKeyService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdvertKeyServiceImpl extends BaseService<AdvertKey> implements AdvertKeyService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ImageConfig imageConfig;
    
    @Autowired
    private AdvertKeyMapper advertKeyMapper;
    
    @Autowired
    private AdvertMapper advertMapper;
    
    @Override
    public List<AdvertKey> findAll(AdvertKey key) {
        Example example = new Example(AdvertKey.class);
        Criteria criteria = example.createCriteria();
        if (StringUtils.isNotBlank(key.getAdKey())) {
            criteria.andCondition("adKey like ", "%" + key.getAdKey() + "%");
        }
        if (StringUtils.isNotBlank(key.getKeyName())) {
            criteria.andCondition("keyName = ", "%" + key.getKeyName() + "%");
        }
        if (null != key.getStatus()) {
            criteria.andCondition("status = ", key.getStatus());
        }
        example.setOrderByClause("status desc, createTime asc");
        return this.selectByExample(example);
    }

    @Override
    public ResponseBo addAdKey(AdvertKey key) {
        this.advertKeyMapper.insertSelective(key);
        return ResponseBo.ok("新增广告位置成功！");
    }

    @Override
    public ResponseBo updateAdKey(AdvertKey key) {
        this.updateNotNull(key);
        return ResponseBo.ok("修改广告位置成功！");
    }
    
    private ResponseBo checkKey(String adKey) {
        Example example = new Example(Advert.class);
        example.createCriteria().andEqualTo("adKey", adKey);
        List<Advert> adverts = this.advertMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(adverts)) {
            return ResponseBo.error("位置不能删除，有广告使用选中的位置！");
        }
        return ResponseBo.ok();
    }

    @Override
    public ResponseBo deleteAdKeys(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        for(String adKey : list) {
            //检查是否有缴费已经用了该账户
            ResponseBo r = checkKey(adKey);
            if (!r.success()) return r;
        }
        
        this.batchDelete(list, "adKey", AdvertKey.class);
        return ResponseBo.ok("删除广告位置成功！");
    }

    @Override
    public AdvertKey findByKey(String adKey) {
        AdvertKey key = this.selectByKey(adKey);
        return key;
    }
}
