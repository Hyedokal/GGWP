package com.ggwp.noticeservice.service;

import com.ggwp.noticeservice.common.dto.RequestFeignDto;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.dto.RequestUpdateNoticeDto;

public interface NoticeService {

    Notice createNotice(RequestFeignDto requestFeignDto);

    Notice findNotice(Long id);
    void update(RequestUpdateNoticeDto requestUpdateNoticeDto);
}
