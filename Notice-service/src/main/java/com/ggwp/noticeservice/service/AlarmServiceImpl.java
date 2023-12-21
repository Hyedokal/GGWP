package com.ggwp.noticeservice.service;

import com.ggwp.noticeservice.dto.ResponseNoticeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {

    private final SimpMessageSendingOperations messagingTemplate;

    public void alarmByMessage(ResponseNoticeDto noticeDto) {
        System.out.println("noticeDto.getReceiverName() = " + noticeDto.getReceiverName());
        System.out.println("noticeDto.getReceiverTag() = " + noticeDto.getReceiverTag());
        messagingTemplate.convertAndSend("/sub/" + noticeDto.getReceiverName() +"#" + noticeDto.getReceiverTag(), noticeDto);
    }

}

