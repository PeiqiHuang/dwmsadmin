package com.dwms.album.service.impl;

import java.awt.Image;
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

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.album.dao.AlbumImgMapper;
import com.dwms.album.dao.AlbumMapper;
import com.dwms.album.domain.Album;
import com.dwms.album.service.AlbumService;
import com.dwms.common.config.ImageConfig;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.PathUtils;

@Service("albumService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AlbumServiceImpl extends BaseService<Album> implements AlbumService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImageConfig imageConfig;
    
    @Autowired
    private AlbumMapper albumMapper;
    
    @Autowired
    private AlbumImgMapper albumImgMapper;
    
    @Override
    public List<Album> findAllAlbums(Album album) {
    	try {
			Example example = new Example(Album.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("status != ", Album.STATUS_DELETE);//不显示状态为删除的
			if (null != album.getPartyId()) {
				criteria.andCondition("partyId = ", album.getPartyId());
			}
			if (StringUtils.isNotBlank(album.getAlbumName())) {
				criteria.andCondition("albumName like ", "%" + album.getAlbumName() + "%");
			}
			if (null != album.getStatus()) {
				criteria.andCondition("status = ", album.getStatus());
			}
			if (StringUtils.isNotBlank(album.getTimeField())) {
                String[] timeArr = album.getTimeField().split("~");
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') <=", timeArr[1]);
            }
			example.setOrderByClause("status desc, createTime desc");
			List<Album> list = this.selectByExample(example);
			PathUtils.setImgAccessPath(list, "cover");
			return list;
		} catch (Exception e) {
			log.error("获取相册列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public ResponseBo addAlbum(Album album) {
        if (album.getStatus() == Album.STATUS_VALID) {
            album.setReleaseTime(new Date());
        }
        this.albumMapper.insertSelective(album);
        return ResponseBo.ok("新增相册成功！");
    }

	@Override
    @Transactional
    public ResponseBo updateAlbum(Album album) {
	    if (album.getStatus() == Album.STATUS_VALID) {
	        album.setReleaseTime(new Date());
	    }
        this.updateNotNull(album);
        return ResponseBo.ok("修改相册成功！");
    }
	
	@Override
	@Transactional
	public ResponseBo deleteAlbums(String ids) {
	    List<String> list = Arrays.asList(ids.split(","));
	    Album album = new Album();
	    album.setStatus(Album.STATUS_DELETE);
	    Example example = new Example(Album.class);
	    example.createCriteria().andIn("albumId", list);
	    this.albumMapper.updateByExampleSelective(album , example);

        return ResponseBo.ok("删除相册成功！");
	}

	@Override
	public Album findById(Integer albumId) {
	    Album a = this.selectByKey(albumId);
	    PathUtils.setAccessPath(a, "cover", imageConfig.getAccessPath());
	    return a;
	}

}
