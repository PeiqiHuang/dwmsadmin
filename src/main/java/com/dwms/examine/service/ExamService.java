package com.dwms.examine.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.examine.domain.Exam;
import com.dwms.examine.domain.vo.ExamWithUser;

public interface ExamService extends IService<Exam> {

    List<Exam> findAllExams(Exam exam);

    void addExam(Exam exam, String[] users);
    
    void copyExam(Exam exam, String[] users);

    ResponseBo updateExam(Exam exam, String[] users);

    ResponseBo deleteExams(String ids);

	ExamWithUser findById(Integer examId);

    ResponseBo makeup(Integer examId);


}
