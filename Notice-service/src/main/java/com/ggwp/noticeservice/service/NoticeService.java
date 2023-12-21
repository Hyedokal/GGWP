package com.ggwp.noticeservice.service;

import com.ggwp.noticeservice.common.dto.RequestDto;
import com.ggwp.noticeservice.common.dto.RequestFeignDto;
import com.ggwp.noticeservice.common.dto.RequestStatusDto;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.dto.RequestUpdateNoticeDto;
import com.ggwp.noticeservice.dto.ResponseNoticeDto;

import java.util.List;

public interface NoticeService {

    Notice createNotice(RequestFeignDto requestFeignDto);

    Notice findNotice(Long id);
    void update(RequestUpdateNoticeDto requestUpdateNoticeDto);

    List<ResponseNoticeDto> getAllNoticeList(RequestDto requestDto);

    List<ResponseNoticeDto> getNoticesByStatus(RequestStatusDto requestStatusDto);
}
