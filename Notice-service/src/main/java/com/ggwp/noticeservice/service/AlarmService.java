package com.ggwp.noticeservice.service;

import com.ggwp.noticeservice.dto.ResponseNoticeDto;
import com.ggwp.noticeservice.dto.SampleDto;

public interface AlarmService {
    void alarmByMessage(ResponseNoticeDto noticeDto);

    void sampleMessage(SampleDto sampleDto);
}
