package com.dwms.meeting.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import tk.mybatis.mapper.entity.Example;

import com.dwms.common.config.FileConfig;
import com.dwms.common.config.HtmlConfig;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.FileUtils;
import com.dwms.common.util.PathUtils;
import com.dwms.meeting.dao.MtgSumUserMapper;
import com.dwms.meeting.dao.MtgSummaryMapper;
import com.dwms.meeting.dao.MtgUserMapper;
import com.dwms.meeting.domain.MtgSumUser;
import com.dwms.meeting.domain.MtgSummary;
import com.dwms.meeting.domain.form.MtgSummaryForm;
import com.dwms.meeting.domain.vo.MtgSumWithUser;
import com.dwms.meeting.service.MtgSummaryService;
import com.dwms.system.domain.SysUser;
import com.google.common.collect.Lists;

@Service("mtgSummaryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MtgSummaryServiceImpl extends BaseService<MtgSummary> implements MtgSummaryService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HtmlConfig htmlConfig;
    @Autowired
    private FileConfig fileConfig;
    
    @Autowired
    private MtgSummaryMapper mtgSummaryMapper;

    @Autowired
    private MtgUserMapper mtgUserMapper;
    
    @Autowired
    private MtgSumUserMapper mtgSumUserMapper;

	@Override
	public List<MtgSummary> findAll(MtgSummary sum) {
		if (null == sum.getMtgId()) {
			log.error("获取会议纪要失败，会议ID缺失");
			return new ArrayList<>();
		}
		try {
			/*Example example = new Example(MtgSummary.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("mtgId = ", sum.getMtgId());
			if (null != sum.getSource()) {
				criteria.andCondition("source = ", sum.getSource());
			}
			if (null != sum.getStatus()) {
				criteria.andCondition("status = ", sum.getStatus());
			}
			example.setOrderByClause("status desc, createTime asc");
			return this.selectByExample(example);*/
			return this.mtgSummaryMapper.findAll(sum);
		} catch (Exception e) {
			log.error("获取会议纪要列表失败", e);
			return new ArrayList<>();
		}
	}

	@Override
	@Transactional
	public ResponseBo addMtgSummary(MtgSummaryForm form) {
	    MtgSummary sum = new MtgSummary();
	    try {
            BeanUtils.copyProperties(sum, form);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResponseBo.error("新增会议纪要失败，请联系网站管理员！");
        }
		if (sum.getStatus() == MtgSummary.STATUS_VALID) {
			MtgSummary query = new MtgSummary();
			query.setMtgId(sum.getMtgId());
			List<MtgSummary> list = findAll(query);
			for (MtgSummary ms : list) {
				if (ms.getStatus() == MtgSummary.STATUS_VALID)
					return ResponseBo.error("已有会议纪已发布，只能发布一篇会议纪要！"); 
			}
			sum.setReleaseTime(new Date());
		}
		sum.setSource(MtgSummary.SOURCE_ADMIN);
		SysUser su = (SysUser) SecurityUtils.getSubject().getPrincipal();
		sum.setOperator(su.getUserId());
		this.mtgSummaryMapper.insertSelective(sum);
		// 保存编辑器内容
		saveContent(sum);
		//插入允许查看的人员
		addMtgSumUser(sum, Arrays.asList(form.getUsers()));
		String subPath = fileConfig.getMtgSummary() + "/" + sum.getSumId();
		//上传文件
		MultipartFile[] files = form.getFiles();
		if (null != files) {
		    ResponseBo r = FileUtils.saveFile(files, subPath);
            if (!r.success()) return r;
		}
		return ResponseBo.ok("新增会议纪要成功！"); 
    }

    private void saveContent(MtgSummary sum) {
    	String root = htmlConfig.getUploadPath();
    	String path= htmlConfig.getRoot() + htmlConfig.getMtgSummary() + "/" + sum.getSumId() + "/";
    	//删除旧文件（清空dir）
    	FileUtils.clearDir(root + path);
    	String fileName = System.currentTimeMillis() + HtmlConfig.HTML;
    	FileUtils.saveByString(root + path, sum.getContent(), fileName);
    	//保存文件路径
    	sum.setFilePath(path + fileName);
    	this.updateNotNull(sum);
	}

	private void updateMtgSumUser(MtgSummary sum, List<String> userList) {
//    	Example example = new Example(MtgUser.class);
//    	example.createCriteria().andCondition("mtgId = ", sum.getMtgId());
//    	List<MtgUser> muList = this.mtgUserMapper.selectAll();
//    	
//    	for(MtgUser mu :muList) {
//    		int readSummary = MtgUser.READ_SUMMARY_NO;
//	    	for(String userId :users) {
//	    		if (StringUtils.equals(userId, mu.getUserId())) {
//	    			readSummary = MtgUser.READ_SUMMARY_YES;
//	    			break;
//	    		}
//	    	}
//	    	mu.setReadSummary(readSummary);
//	    	this.mtgUserMapper.updateByPrimaryKey(mu);
//    	}
    	Example example = new Example(MtgSumUser.class);
		example.createCriteria().andCondition("sumId=", sum.getSumId());
		List<MtgSumUser> msuList = this.mtgSumUserMapper.selectByExample(example);
		
		List<String> msuUserList = Lists.newArrayList();
		for (MtgSumUser mu : msuList) {
			msuUserList.add(mu.getUserId());
		}
		// subtract(a,b) 截取a有的b没有的
		List<String> delUsers = ListUtils.subtract(msuUserList, userList);
		List<String> addUsers = ListUtils.subtract(userList, msuUserList);
		//delete
		if (!delUsers.isEmpty()) {
			example = new Example(MtgSumUser.class);
			example.createCriteria().andCondition("sumId=", sum.getSumId()).andIn("userId", delUsers);
			this.mtgSumUserMapper.deleteByExample(example);
		}
		//insert
		addMtgSumUser(sum, addUsers);
	}
    
    private void addMtgSumUser(MtgSummary sum, List<String> users) {
    	for(String userId :users) {
    		MtgSumUser msu = new MtgSumUser();
    		msu.setSumId(sum.getSumId());
    		msu.setUserId(userId);
    		this.mtgSumUserMapper.insertSelective(msu);
    	}
	}

	@Override
	@Transactional
	public ResponseBo updateMtgSummary(MtgSummaryForm form) {
	    MtgSummary sum = new MtgSummary();
        try {
            BeanUtils.copyProperties(sum, form);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return ResponseBo.error("修改会议纪要失败，请联系网站管理员！");
        }
		if (sum.getStatus() == MtgSummary.STATUS_VALID) {
			MtgSummary query = new MtgSummary();
			query.setMtgId(sum.getMtgId());
			List<MtgSummary> list = findAll(query);
			for (MtgSummary ms : list) {
				if (ms.getStatus() == MtgSummary.STATUS_VALID)
					return ResponseBo.error("已有会议纪已发布，只能发布一篇会议纪要！"); 
			}
			sum.setReleaseTime(new Date());
		}
		String subPath = fileConfig.getMtgSummary() + "/" + sum.getSumId();
		//上传文件
		MultipartFile[] files = form.getFiles();
        if (null != files) {
            ResponseBo r = FileUtils.saveFile(files, subPath);
            if (!r.success()) return r;
        }
        //删除文件
        FileUtils.delFiles(form.getDelFileNames(), subPath);
        
		this.updateNotNull(sum);
		// 保存编辑器内容
		saveContent(sum);
		//更新允许查看的人员
		updateMtgSumUser(sum, Arrays.asList(form.getUsers()));
		return ResponseBo.ok("修改会议纪要成功！");
	}

	@Override
	@Transactional
	public ResponseBo deleteMtgSummarys(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		List<MtgSummary> mtgList = Lists.newArrayList();
		for (String id : list) {
			MtgSummary sum = selectByKey(NumberUtils.toInt(id));
			if (sum.getSource() == MtgSummary.SOURCE_ADMIN) {
				sum.setStatus(MtgSummary.STATUS_DELETE);
				mtgList.add(sum);
			} else {
				return ResponseBo.error("删除会议失败，所选的会议只能是后台创建的！"); 
			}
		}
		for(MtgSummary mtg : mtgList) {
			this.updateNotNull(mtg);
		}
		return ResponseBo.ok("删除会议成功！");
	}

	@Override
	public List<MtgSumUser> findMtgSumUsers(Integer sumId) {
		Example example = new Example(MtgSumUser.class);
		example.createCriteria().andCondition("sumId=", sumId);
		return this.mtgSumUserMapper.selectByExample(example);
	}

	@Override
	public MtgSumWithUser findMtgSumById(Integer sumId) {
		List<MtgSumWithUser> list = this.mtgSummaryMapper.findById(sumId);
		if (list.isEmpty()) return null;
		List<String> users = Lists.newArrayList();
		for (MtgSumWithUser mtg : list) {
			users.add(mtg.getUserId());
		}
		MtgSumWithUser vo = list.get(0);
		vo.setUserIds(users);
		
		//编辑器内容
		getContent(vo);
		
		//获取附件
		vo.setFiles(PathUtils.getAccessFiles(fileConfig.getMtgSummary() + "/" + sumId));
		
		return vo;
	}

	private void getContent(MtgSumWithUser sum) {
    	String fileName = htmlConfig.getUploadPath() + sum.getFilePath();
    	sum.setContent(FileUtils.getFile(fileName));
	}

}
