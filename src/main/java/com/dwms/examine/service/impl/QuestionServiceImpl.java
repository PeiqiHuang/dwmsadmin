package com.dwms.examine.service.impl;

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
import com.dwms.examine.dao.ExamQuMapper;
import com.dwms.examine.dao.QuestionMapper;
import com.dwms.examine.domain.ExamQu;
import com.dwms.examine.domain.Question;
import com.dwms.examine.service.QuestionService;

@Service("questionService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuestionServiceImpl extends BaseService<Question> implements QuestionService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private ExamQuMapper examQuMapper;
    
    @Override
    public List<Question> findAllQuestions(Question question) {
    	try {
			Example example = new Example(Question.class);
			Criteria criteria = example.createCriteria();
			if (null != question.getPartyId()) {
				criteria.andCondition("partyId = ", question.getPartyId());
			}
			if (StringUtils.isNotBlank(question.getTitle())) {
				criteria.andCondition("title like ", "%" + question.getTitle() + "%");
			}
			if (StringUtils.isNotBlank(question.getAnswers())) {
			    criteria.andCondition("answers like ", "%" + question.getAnswers() + "%");
			}
			if (StringUtils.isNotBlank(question.getRightKey())) {
			    criteria.andCondition("rightKey like ", "%" + question.getRightKey() + "%");
			}
			if (null != question.getQuType()) {
			    criteria.andCondition("quType = ", question.getQuType());
			}
			if (null != question.getStatus()) {
				criteria.andCondition("status = ", question.getStatus());
			}
			if (StringUtils.isNotBlank(question.getTimeField())) {
                String[] timeArr = question.getTimeField().split("~");
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') >=", timeArr[0]);
                criteria.andCondition("date_format(createTime,'%Y-%m-%d') <=", timeArr[1]);
            }
			example.setOrderByClause("status desc, createTime desc");
			return this.selectByExample(example);
		} catch (Exception e) {
			log.error("获取题目列表失败", e);
			return new ArrayList<>();
		}
    }
    
    @Override
    public List<Question> searchQuestions(Question question) {
        try {
            Example example = new Example(Question.class);
            Criteria criteria = example.createCriteria();
            criteria.andCondition("partyId = ", question.getPartyId());
            criteria.andCondition("status = ", 1);
            if (null != question.getQuType()) {
                criteria.andCondition("quType = ", question.getQuType());
            }
            if (StringUtils.isNotBlank(question.getTitle())) {
                Criteria or = example.createCriteria();
                or.orCondition("title like ", "%" + question.getTitle() + "%");
                or.orCondition("answers like ", "%" + question.getTitle() + "%");
                or.orCondition("rightKey like ", "%" + question.getTitle() + "%");
                example.and(or);
            }
            example.setOrderByClause("status desc, createTime desc");
            return this.selectByExample(example);
        } catch (Exception e) {
            log.error("搜索题目列表失败", e);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional
    public void addQuestion(Question question) {
        this.questionMapper.insertSelective(question);
    }

	@Override
    @Transactional
    public void updateQuestion(Question question) {
        this.updateNotNull(question);
    }

	@Override
	@Transactional
	public ResponseBo deleteQuestions(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		for (String id : list) {
			Question question = selectByKey(NumberUtils.toInt(id));
            Example example = new Example(ExamQu.class);
            example.createCriteria().andCondition("examId = ", id);
            List<ExamQu> qus = this.examQuMapper.selectByExample(example);
            if (CollectionUtils.isNotEmpty(qus))
                return ResponseBo.error(String.format("删除题目失败，所选的题目考试'%s'已采用！", question.getTitle())); 
		}
		this.batchDelete(list, "quId", Question.class);
		return ResponseBo.ok("删除题目成功！");
	}

}
