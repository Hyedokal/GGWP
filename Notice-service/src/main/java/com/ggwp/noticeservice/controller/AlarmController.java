package com.ggwp.noticeservice.controller;

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

    // stomp 테스트 화면
    @GetMapping(value = "stomp", produces = MediaType.TEXT_HTML_VALUE)
    public ResponseEntity<Resource> stompAlarm() {
        Resource htmlFile = new ClassPathResource("static/stomp.html");
        return ResponseEntity.ok().body(htmlFile);
    }
    @MessageMapping("/{lolname}/{tagLine}")
    public void message(@DestinationVariable("lolname") String lolname,@DestinationVariable("tagLine") String tagLine) {
        messagingTemplate.convertAndSend("/sub/" + lolname + "#" + tagLine, "alarm socket connection completed.");
    }
}



