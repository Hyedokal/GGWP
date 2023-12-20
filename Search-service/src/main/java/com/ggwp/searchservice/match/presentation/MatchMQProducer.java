package com.ggwp.searchservice.match.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MatchMQProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendCreateMessage(String message) {
        System.out.println("프로듀서 : " + message);
        rabbitTemplate.convertAndSend("MATCH_QUEUE", message);

    }
}
