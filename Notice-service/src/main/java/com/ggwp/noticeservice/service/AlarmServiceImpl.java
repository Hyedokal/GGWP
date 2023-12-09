package com.ggwp.noticeservice.service;

import com.ggwp.noticeservice.dto.ResponseNoticeDto;
import com.ggwp.noticeservice.dto.SampleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void alarmByMessage(ResponseNoticeDto noticeDto) {
        messagingTemplate.convertAndSend("/sub/" + noticeDto.getReceiverName(), noticeDto);
    }

    public void sampleMessage(SampleDto sampleDto) {
        messagingTemplate.convertAndSend("/sub/" + sampleDto.getReceiverId(), sampleDto);
    }

}

