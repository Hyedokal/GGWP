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


    @MessageMapping("/{lolname}/{tagLine}")
    public void message(@DestinationVariable("lolname") String lolname,@DestinationVariable("tagLine") String tagLine) {
        messagingTemplate.convertAndSend("/sub/" + lolname + "#" + tagLine, "alarm socket connection completed.");
    }
}



