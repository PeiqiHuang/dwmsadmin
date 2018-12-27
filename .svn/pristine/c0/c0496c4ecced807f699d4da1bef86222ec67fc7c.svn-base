package com.dwms.advert.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.advert.dao.AdvertKeyMapper;
import com.dwms.advert.dao.AdvertMapper;
import com.dwms.advert.domain.Advert;
import com.dwms.advert.service.AdvertService;
import com.dwms.common.config.ImageConfig;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.FileUtils;
import com.dwms.common.util.PathUtils;
import com.dwms.course.domain.Course;

@Service("advertService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdvertServiceImpl extends BaseService<Advert> implements AdvertService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImageConfig imageConfig;
    
    @Autowired
    private AdvertMapper advertMapper;
    
    @Autowired
    private AdvertKeyMapper advertKeyMapper;
    
    @Override
    public List<Advert> findAllAdverts(Advert advert) {
    	try {
    	    List<Advert> list = this.advertMapper.findAllAdverts(advert);
    	    PathUtils.setImgAccessPath(list, "cover");
    	    return list;
		} catch (Exception e) {
			log.error("获取广告列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public ResponseBo addAdvert(Advert advert) {
        this.advertMapper.insertSelective(advert);
        //上传封面
        MultipartFile file = advert.getCoverImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, imageConfig.getAdvert() + "/" + advert.getAdId());
            if (r.success()) {
                advert.setCover((String)r.getMsg());
            } else {
                return r;
            }
        }
        this.updateNotNull(advert);
        return ResponseBo.ok("新增广告成功！");
    }
    
	@Override
    @Transactional
    public ResponseBo updateAdvert(Advert advert) {
	    //上传封面
        MultipartFile file = advert.getCoverImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, imageConfig.getAdvert() + "/" + advert.getAdId());
            if (r.success()) {
                advert.setCover((String)r.getMsg());
            } else {
                return r;
            }
        }
        this.updateNotNull(advert);
        return ResponseBo.ok("修改广告成功！");
    }

	@Override
	@Transactional
	public ResponseBo deleteAdverts(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		this.batchDelete(list, "adId", Advert.class);
		return ResponseBo.ok("删除广告成功！");
	}

    @Override
    public Advert findById(Integer adId) {
        Advert a = this.selectByKey(adId);
        PathUtils.setAccessPath(a, "cover", imageConfig.getAccessPath());
        return a;
    }
	
}
