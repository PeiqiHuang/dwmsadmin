package com.dwms.examine.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.examine.domain.ExamUser;
import com.dwms.examine.domain.form.ExamQuUserForm;
import com.dwms.examine.domain.vo.ExamQuUserVo;

public interface ExamUserService extends IService<ExamUser> {

    List<ExamUser> findAllByExamId(ExamUser examUser);

    List<ExamQuUserVo> findExamUserQu(ExamQuUserForm form);

    ResponseBo judgeScore(Integer equId, Integer score);

}
