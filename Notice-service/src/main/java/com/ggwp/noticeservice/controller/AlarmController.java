package com.ggwp.noticeservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Alarm", description = "Alarm API")
public class AlarmController {

    private final SimpMessageSendingOperations messagingTemplate;


    @MessageMapping("/{lolname}/{tagLine}")
    @Operation(summary = "알람 보내기", description = "소켓을 통한 알림 메시지 보내기")
    public void message(@DestinationVariable("lolname") String lolname,@DestinationVariable("tagLine") String tagLine) {
        messagingTemplate.convertAndSend("/sub/" + lolname + "#" + tagLine, "alarm socket connection completed.");
    }
}



