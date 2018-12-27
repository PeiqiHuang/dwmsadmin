package com.dwms.notice.service;

import java.util.List;

import com.dwms.common.domain.ResponseBo;
import com.dwms.common.service.IService;
import com.dwms.notice.domain.Notice;

public interface NoticeService extends IService<Notice> {

    List<Notice> findAllNotices(Notice notice);

    ResponseBo addNotice(Notice notice);
    
    ResponseBo updateNotice(Notice notice);

    ResponseBo deleteNotices(String ids);

	Notice findById(Integer noticeId);

}
