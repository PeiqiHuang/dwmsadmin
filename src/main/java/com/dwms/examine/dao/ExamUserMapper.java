package com.dwms.examine.dao;

import java.util.List;

import com.dwms.common.config.MyMapper;
import com.dwms.examine.domain.ExamUser;
import com.dwms.examine.domain.form.ExamQuUserForm;
import com.dwms.examine.domain.vo.ExamQuUserVo;

public interface ExamUserMapper extends MyMapper<ExamUser> {

    List<ExamUser> findAllByExamId(ExamUser examUser);

    List<ExamQuUserVo> findExamUserQu(ExamQuUserForm form);
}