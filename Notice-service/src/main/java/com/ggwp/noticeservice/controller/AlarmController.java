package com.ggwp.noticeservice.controller;

import com.ggwp.noticeservice.dto.SampleDto;
import com.ggwp.noticeservice.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice-service/v1/alarm")
public class AlarmController {

    private final SimpMessageSendingOperations messagingTemplate;

    private final AlarmService alarmService;
    // stomp 테스트 화면
    @GetMapping(value = "stomp", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Resource> stompAlarm() {
        Resource htmlFile = new ClassPathResource("static/stomp.html");
        return ResponseEntity.ok().body(htmlFile);
    }
    @PostMapping("alarm")
    public ResponseEntity<?> alarmexample(@RequestBody SampleDto sampleDto){
        alarmService.sampleMessage(sampleDto);
        return ResponseEntity.ok("good!");
    }

    @MessageMapping("/{userId}")
    public void message(@DestinationVariable("userId") Long userId) {
        messagingTemplate.convertAndSend("/sub/" + userId, "alarm socket connection completed.");
    }
}



