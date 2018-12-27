package com.dwms.due.service.impl;

import java.util.Arrays;
import java.util.List;

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

import com.dwms.common.config.ImageConfig;
import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.common.util.FileUtils;
import com.dwms.common.util.PathUtils;
import com.dwms.due.dao.DueAccountMapper;
import com.dwms.due.dao.DueMapper;
import com.dwms.due.domain.Due;
import com.dwms.due.domain.DueAccount;
import com.dwms.due.service.DueAccountService;

@Service("dueAccountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class DueAccountServiceImpl extends BaseService<DueAccount> implements DueAccountService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ImageConfig imageConfig;
    
    @Autowired
    private DueMapper dueMapper;
    
    @Autowired
    private DueAccountMapper dueAccountMapper;
    
    @Override
    public List<DueAccount> findAll(DueAccount account) {
        Example example = new Example(DueAccount.class);
        Criteria criteria = example.createCriteria();
        if (null != account.getPartyId()) {
            criteria.andCondition("partyId = ", account.getPartyId());
        }
        if (null != account.getStatus()) {
            criteria.andCondition("status = ", account.getStatus());
        }
        example.setOrderByClause("status desc, createTime desc");
        List<DueAccount> list = this.selectByExample(example);
        
        PathUtils.setImgAccessPath(list, "wechatFilePath");
        PathUtils.setImgAccessPath(list, "alipayFilePath");
        return list;
    }

    @Override
    public ResponseBo addAccount(DueAccount account) {
        
        this.dueAccountMapper.insertSelective(account);
        
        //上传图片
        String path = imageConfig.getDueAccount() + "/" + account.getAccId();
        MultipartFile file = account.getWechatImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, path + imageConfig.getAccountWechat());
            if (r.success()) {
                account.setWechatFilePath((String)r.getMsg());
            } else {
                return r;
            }
        }
        file = account.getAlipayImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, path + imageConfig.getAccountAlipay());
            if (r.success()) {
                account.setAlipayFilePath((String)r.getMsg());
            } else {
                return r;
            }
        }
        this.updateNotNull(account);
        
        return ResponseBo.ok("新增账户信息成功！");
    }

    @Override
    public ResponseBo updateAccount(DueAccount account) {
        //上传图片
        String path = imageConfig.getDueAccount() + "/" + account.getAccId();
        MultipartFile file = account.getWechatImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, path + imageConfig.getAccountWechat());
            if (r.success()) {
                account.setWechatFilePath((String)r.getMsg());
            } else {
                return r;
            }
        }
        file = account.getAlipayImg();
        if (null != file && !file.isEmpty()) {
            ResponseBo r = FileUtils.saveImg(file, path + imageConfig.getAccountAlipay());
            if (r.success()) {
                account.setAlipayFilePath((String)r.getMsg());
            } else {
                return r;
            }
        }
        
        this.updateNotNull(account);
        return ResponseBo.ok("修改账户信息成功！");
    }
    
    private ResponseBo checkAccount(int accId) {
        DueAccount account = this.selectByKey(accId);
        Example example = new Example(Due.class);
        example.createCriteria().andEqualTo("partyId", account.getPartyId());
        List<Due> dues = this.dueMapper.selectByExample(example);
        for (Due due : dues) {
            if (due.getAccId() != null && 
                    due.getAccId() == accId) {
                return ResponseBo.error("账户不能删除，有缴费使用选中的账户信息！");
            }
        }
        return ResponseBo.ok();
    }

    @Override
    public ResponseBo deleteAccounts(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        for(String accId : list) {
            //检查是否有缴费已经用了该账户
            ResponseBo r = checkAccount(NumberUtils.toInt(accId));
            if (!r.success()) return r;
        }
        
        //del imgs dir
        for(String accId : list) {
            FileUtils.delDir(imageConfig.getUploadPath() + imageConfig.getRoot() +
                    imageConfig.getDueAccount() + "/" + accId);
        }
        this.batchDelete(list, "accId", DueAccount.class);
        return ResponseBo.ok("删除账户信息成功！");
    }

    @Override
    public DueAccount findById(Integer accId) {
        DueAccount account = this.selectByKey(accId);
        PathUtils.setAccessPath(account, "wechatFilePath", imageConfig.getAccessPath());
        PathUtils.setAccessPath(account, "alipayFilePath", imageConfig.getAccessPath());
        return account;
    }
}
