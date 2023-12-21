package com.ggwp.noticeservice.service;

import com.ggwp.noticeservice.dto.ResponseNoticeDto;

public interface AlarmService {
    void alarmByMessage(ResponseNoticeDto noticeDto);
}
