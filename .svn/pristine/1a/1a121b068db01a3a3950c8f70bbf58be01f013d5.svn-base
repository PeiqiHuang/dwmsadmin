package com.dwms.examine.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import com.dwms.examine.domain.Exam;
import com.dwms.examine.domain.ExamQu;
import com.dwms.examine.domain.ExamQuUser;
import com.dwms.examine.domain.Question;
import com.dwms.examine.domain.form.ExamQuForm;
import com.dwms.examine.domain.vo.ExamQuWithData;
import com.dwms.examine.domain.vo.ExamQuWithQu;
import com.dwms.examine.service.ExamQuService;
import com.google.common.collect.Lists;

@Service("examQuService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ExamQuServiceImpl extends BaseService<ExamQu> implements ExamQuService {

    private Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private ExamMapper examMapper;

    @Autowired
    private ExamQuMapper examQuMapper;
    
    @Autowired
    private ExamQuUserMapper examQuUserMapper;

    @Override
    public List<ExamQuWithQu> findAllByExamId(ExamQuForm qu) {
        return examQuMapper.findAllByExamId(qu);
    }

    @Override
    public ResponseBo addExamQu(ExamQu examQu) {
        Integer examId = examQu.getExamId();
        if (examLock(examId)) {
            return ResponseBo.error("考试已发布，不能新增试题");
        }
        List<ExamQu> eqs = findExamQusByExamId(examId);
        for (ExamQu eq : eqs) {
            if (eq.getQuId().equals(examQu.getQuId())) {
                return ResponseBo.error("该试题已添加，不能重复添加"); 
            }
        }
        this.examQuMapper.insertSelective(examQu);
        updateExamQuNum(examQu);
        return ResponseBo.ok("新增试题成功！");
    }

    @Override
    public ResponseBo updateExamQu(ExamQu examQu) {
        Integer examId = examQu.getExamId();
        if (examLock(examId)) {
            return ResponseBo.error("考试已发布，不能修改试题");
        }
        List<ExamQu> eqs = findExamQusByExamId(examId);
        for (ExamQu eq : eqs) {
            if (!examQu.getEqId().equals(eq.getEqId())) {
                if (eq.getQuId().equals(examQu.getQuId())) {
                    return ResponseBo.error("该试题已添加，不能重复添加"); 
                }
            }
        }
        this.updateNotNull(examQu);
        return ResponseBo.ok("修改试题成功！");
    }

    @Override
    public ResponseBo deleteExamQus(String ids) {
        List<String> list = Arrays.asList(ids.split(","));
        Integer eqId = NumberUtils.toInt(list.get(0));
        ExamQu examQu = selectByKey(eqId);
        if (examLock(examQu.getExamId())) {
            return ResponseBo.error("考试已发布，不能删除试题");
        }
        this.batchDelete(list, "eqId", ExamQu.class);
        updateExamQuNum(examQu);
        return ResponseBo.ok("删除试题成功！");
    }

    @Override
    public ExamQuWithQu findById(Integer eqId) {
        return this.examQuMapper.findById(eqId);
    }
    
    @Override
    public List<ExamQu> findExamQusByExamId(Integer examId) {
        Example example = new Example(ExamQu.class);
        example.createCriteria().andEqualTo("examId", examId);
        return selectByExample(example);
    }
    
    private boolean examLock(Integer examId) {
        Exam exam = this.examMapper.selectByPrimaryKey(examId);
        return exam.getStatus() == Exam.STATUS_VALID;
    }
    
    private void updateExamQuNum(ExamQu eq) {
        List<ExamQu> eqs = findExamQusByExamId(eq.getExamId());
        int quNum = eqs.size();
        Exam exam = new Exam();
        exam.setExamId(eq.getExamId());
        exam.setQuNum(quNum);
        this.examMapper.updateByPrimaryKeySelective(exam);
    }

    @Override
    public int getTotalScore(Integer examId) {
        return this.examQuMapper.getTotalScore(examId);
    }

    @Override
    public List<ExamQuWithData> findExamQuData(ExamQuForm form) {
        List<ExamQuWithQu> eqs =  findAllByExamId(form);
        List<ExamQuWithData> datas = Lists.newArrayList();
        String separator = ";";
        List<ExamQuUser> equs = null;
        Example example = null;
        ExamQuWithData data = null;
        for (ExamQuWithQu eq : eqs) {
            data = new ExamQuWithData();
            BeanUtils.copyProperties(eq, data);
            datas.add(data);
            int quType = data.getQuType();
            //该题答题情况
            example = new Example(ExamQuUser.class);
            example.createCriteria().andEqualTo("eqId", eq.getEqId());
            equs = this.examQuUserMapper.selectByExample(example);
            //设置选项
            if (quType != Question.QUTYPE_SHORT) {
                String target = data.getAnswers();
                if (quType == Question.QUTYPE_FILL) {
                    target = data.getRightKey();
                }
                StringTokenizer st = new StringTokenizer(target, separator);
                while( st.hasMoreElements() ){
                    String answer = st.nextToken();
                    int selectNum = 0;
                    for(ExamQuUser equ : equs) {
                        if (quType == Question.QUTYPE_SINGLE) {
                            if (StringUtils.equals(equ.getAnswer(), answer)) {
                                selectNum++;
                            }
                        } else if (quType == Question.QUTYPE_MULTIPLE
                                || quType == Question.QUTYPE_FILL) {
                            StringTokenizer st2 = new StringTokenizer(equ.getAnswer(), separator);
                            while( st2.hasMoreElements() ){
                                String item = st2.nextToken();
                                if (StringUtils.equals(item, answer)) {
                                    selectNum++;
                                    break;
                                }
                            }
                        }
                    }
                    float rate = 100 * (selectNum / equs.size());
                    data.addAnswer(answer, selectNum, rate);
                }
            } else {
                float avgScore = 0;
                for(ExamQuUser equ : equs) {
                    avgScore += equ.getScore();
                }
                avgScore = avgScore / equs.size();
                data.addAnswer("", equs.size(), avgScore);
            }
        }
        return datas;
    }
}
