package com.dwms.examine.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.examine.domain.ExamQu;
import com.dwms.examine.domain.form.ExamQuDataForm;
import com.dwms.examine.domain.form.ExamQuForm;
import com.dwms.examine.domain.vo.ExamQuWithData;
import com.dwms.examine.domain.vo.ExamQuWithQu;

public interface ExamQuService extends IService<ExamQu> {

    List<ExamQuWithQu> findAllByExamId(ExamQuForm qu);

    ResponseBo addExamQu(ExamQu examQu);

    ResponseBo updateExamQu(ExamQu examQu);

    ResponseBo deleteExamQus(String ids);

    ExamQuWithQu findById(Integer eqId);
    
    List<ExamQu> findExamQusByExamId(Integer examId);
    
    int getTotalScore(Integer examId);

    List<ExamQuWithData> findExamQuData(ExamQuForm form);
}
