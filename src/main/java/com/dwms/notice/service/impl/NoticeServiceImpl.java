package com.dwms.notice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.dwms.common.config.HtmlConfig;
import com.dwms.common.config.ImageConfig;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.FileUtils;
import com.dwms.common.util.PathUtils;
import com.dwms.course.domain.Course;
import com.dwms.notice.dao.NoticeMapper;
import com.dwms.notice.domain.Notice;
import com.dwms.notice.service.NoticeService;
import com.google.common.collect.Lists;

@Service("noticeService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class NoticeServiceImpl extends BaseService<Notice> implements NoticeService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ImageConfig imageConfig;
    @Autowired
    private HtmlConfig htmlConfig;
    @Autowired
    private NoticeMapper noticeMapper;
    
    @Override
    public List<Notice> findAllNotices(Notice notice) {
    	try {
			Example example = new Example(Notice.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("status != ", Notice.STATUS_DELETE);//不显示状态为删除的
			if (null != notice.getPartyId()) {
				criteria.andCondition("partyId = ", notice.getPartyId());
			}
			if (StringUtils.isNotBlank(notice.getTitle())) {
				criteria.andCondition("title like ", "%" + notice.getTitle() + "%");
			}
			if (null != notice.getType()) {
			    criteria.andCondition("type = ", notice.getType());
			}
			if (null != notice.getStatus()) {
				criteria.andCondition("status = ", notice.getStatus());
			}
			if (StringUtils.isNotBlank(notice.getTimeField())) {
                String[] timeArr = notice.getTimeField().split("~");
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') <=", timeArr[1]);
            }
			example.setOrderByClause("status desc, createTime desc");
			List<Notice> list = this.selectByExample(example);
			PathUtils.setImgAccessPath(list, "cover");
			return list;
		} catch (Exception e) {
			log.error("获取通知列表失败", e);
			return new ArrayList<>();
		}
    }

    @Override
    @Transactional
    public ResponseBo addNotice(Notice notice) {
        //notice.setStatus(Notice.STATUS_WAIT);
        if (notice.getStatus() == Notice.STATUS_VALID) {
            notice.setReleaseTime(new Date());
        }
        this.noticeMapper.insertSelective(notice);
        //上传封面
        MultipartFile file = notice.getImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, imageConfig.getNotice() + "/" + notice.getNtiId());
            if (r.success()) {
                notice.setCover((String)r.getMsg());
            } else {
                return r;
            }
        }
        this.updateNotNull(notice);
        // 保存编辑器内容
        saveContent(notice);
        return ResponseBo.ok("新增通知成功！");
    }

	@Override
    @Transactional
    public ResponseBo updateNotice(Notice notice) {
	    boolean noEdit = (null == notice.getStatus());//发布后修改为null

	    notice.setUpdateTime(new Date());
        this.updateNotNull(notice);
        //上传封面
        MultipartFile file = notice.getImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, imageConfig.getNotice() + "/" + notice.getNtiId());
            if (r.success()) {
                notice.setCover((String)r.getMsg());
            } else {
                return r;
            }
        }
        
        if (!noEdit && notice.getStatus() == Notice.STATUS_VALID) {
            notice.setReleaseTime(new Date());
        }
        this.updateNotNull(notice);
        // 保存编辑器内容
        saveContent(notice);

        return ResponseBo.ok("修改通知成功！");
    }

	private void saveContent(Notice notice) {
        String root = htmlConfig.getUploadPath();
        String path= htmlConfig.getRoot() + htmlConfig.getNotice() + "/" + notice.getNtiId() + "/";
        //删除旧文件（清空dir）
        FileUtils.clearDir(root + path);
        String fileName = System.currentTimeMillis() + HtmlConfig.HTML;
        FileUtils.saveByString(root + path, notice.getContent(), fileName);
        //保存文件路径
        notice.setFilePath(path + fileName);
        this.updateNotNull(notice);
    }
	
	@Override
	@Transactional
	public ResponseBo deleteNotices(String ids) {
	    List<String> list = Arrays.asList(ids.split(","));
        List<Notice> delList = Lists.newArrayList();
        for (String id : list) {
            Notice notice = selectByKey(NumberUtils.toInt(id));
            int status = notice.getStatus();
            if (status != Notice.STATUS_VALID) {
                notice.setStatus(Notice.STATUS_DELETE);
                delList.add(notice);
            } else {
                return ResponseBo.error("删除通知失败，所选的通知只能是已取消、待发布的！"); 
            }
        }
        for(Notice notice : delList) {
            this.updateNotNull(notice);
        }
        return ResponseBo.ok("删除通知成功！");
	}

	@Override
	public Notice findById(Integer ntiId) {
	    Notice notice = this.noticeMapper.selectByPrimaryKey(ntiId);
		//编辑器内容
        getContent(notice);
        
        PathUtils.setAccessPath(notice, "cover", imageConfig.getAccessPath());
		
		return notice;
	}
	
	private void getContent(Notice notice) {
        String fileName = htmlConfig.getUploadPath() + notice.getFilePath();
        notice.setContent(FileUtils.getFile(fileName));
    }
}
