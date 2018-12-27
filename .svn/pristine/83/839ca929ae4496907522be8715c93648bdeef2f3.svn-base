package com.dwms.meeting.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.meeting.domain.MtgSumUser;
import com.dwms.meeting.domain.MtgSummary;
import com.dwms.meeting.domain.form.MtgSummaryForm;
import com.dwms.meeting.domain.vo.MtgSumWithUser;

public interface MtgSummaryService extends IService<MtgSummary> {

	List<MtgSummary> findAll(MtgSummary sum);

	ResponseBo addMtgSummary(MtgSummaryForm sum);

	ResponseBo updateMtgSummary(MtgSummaryForm sum);

	ResponseBo deleteMtgSummarys(String ids);

	List<MtgSumUser> findMtgSumUsers(Integer sumId);

	MtgSumWithUser findMtgSumById(Integer sumId);
}
