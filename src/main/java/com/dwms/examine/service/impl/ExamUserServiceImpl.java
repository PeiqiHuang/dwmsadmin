package com.dwms.examine.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.impl.BaseService;
import com.dwms.examine.dao.ExamMapper;
import com.dwms.examine.dao.ExamQuMapper;
import com.dwms.examine.dao.ExamQuUserMapper;
import com.dwms.examine.dao.ExamUserMapper;
import com.dwms.examine.domain.Exam;
import com.dwms.examine.domain.ExamQu;
import com.dwms.examine.domain.ExamQuUser;
import com.dwms.examine.domain.ExamUser;
import com.dwms.examine.domain.form.ExamQuUserForm;
import com.dwms.examine.domain.vo.ExamQuUserVo;
import com.dwms.examine.service.ExamUserService;

@Service("examUserService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ExamUserServiceImpl extends BaseService<ExamUser> implements ExamUserService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ExamUserMapper examUserMapper;
    
    @Autowired
    private ExamMapper examMapper;
    
    @Autowired
    private ExamQuMapper examQuMapper;
    
    @Autowired
    private ExamQuUserMapper examQuUserMapper;

    @Override
    public List<ExamUser> findAllByExamId(ExamUser examUser) {
        return examUserMapper.findAllByExamId(examUser);
    }

    @Override
    public List<ExamQuUserVo> findExamUserQu(ExamQuUserForm form) {
        return examUserMapper.findExamUserQu(form);
    }

    @Override
    public ResponseBo judgeScore(Integer equId, Integer score) {
        ExamQuUser equ = this.examQuUserMapper.selectByPrimaryKey(equId);
        Integer eqId = equ.getEqId();
        Integer euId = equ.getEuId();
        ExamQu eq = examQuMapper.selectByPrimaryKey(eqId);
        if (score > eq.getScore()) 
            return ResponseBo.error("得分不能超过题目分数！");
        
        equ.setScore(score);
        equ.setStatus(ExamQuUser.STATUS_JUDGE);
        examQuUserMapper.updateByPrimaryKey(equ);
        //更新考试总分
        updateExamUser(euId);
        return ResponseBo.ok("题目判分成功！");
    }
    
    private void updateExamUser(Integer euId) {
        
        List<ExamQuUser> all = findAllExamQuUsers(euId);
        Integer newScore = 0;
        boolean judgeAll = true;
        for (ExamQuUser item : all) {
            newScore += item.getScore();
            if (item.getStatus() == ExamQuUser.STATUS_NOT_JUDGE) {
                judgeAll = false;
            }
        }
        ExamUser eu =examUserMapper.selectByPrimaryKey(euId);
        eu.setScore(newScore);
        // 设置考试状态
        if (judgeAll) {
            Integer examId = eu.getExamId();
            Exam exam = examMapper.selectByPrimaryKey(examId);
            if (newScore < exam.getPassScore()) {
                eu.setStatus(ExamUser.STATUS_FAIL);
            } else {
                eu.setStatus(ExamUser.STATUS_PASS);
            }
        }
        examUserMapper.updateByPrimaryKey(eu);
    }

    //根据其中一题获取所有考题答题情况
    private List<ExamQuUser> findAllExamQuUsers(Integer euId) {
        Example example = new Example(ExamQuUser.class);
        example.createCriteria().andEqualTo("euId", euId);
        return examQuUserMapper.selectByExample(example);
    }

}
