package com.ggwp.searchservice.match.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;

@Component
@RequiredArgsConstructor
public class MatchMQConsumer {
    private final ObjectMapper objectMapper;
    private final MatchService matchService;

    // 슬라이딩 윈도우 큐
    private final Queue<Long> windowQueue = new LinkedList<>();
    private final int maxRequestsPerSecond = 20;
    private final int maxRequestsPerTwoMinutes = 100;

    @RabbitListener(queues = "MATCH_QUEUE")
    public void createMatch(String message) throws JsonProcessingException, InterruptedException {
        long currentTime = System.currentTimeMillis();

        // 현재 시간을 큐에 추가
        windowQueue.offer(currentTime);

        // 2분 동안의 요청 수가 허용된 최대 요청 수를 초과하면 메시지를 출력하고 메서드 종료
        if (windowQueue.size() > maxRequestsPerTwoMinutes) {
            System.out.println("Too many requests for the last two minutes. Ignoring the message: " + message);
            return;
        }

        // 윈도우 큐의 크기가 허용된 최대 요청 수보다 크면 초과된 만큼 메시지를 출력하고 메서드 종료
        if (windowQueue.size() > maxRequestsPerSecond) {
            Thread.sleep(1000);
            System.out.println("초과되어 1초 쉬고 다시 컨슈머 : " + message);
            FrontDto frontDto = objectMapper.readValue(message, FrontDto.class);
            matchService.createMatches(frontDto);
            return; // 추가된 부분
        }

        // 큐의 크기가 최대 요청 수 이하일 때만 메서드 호출
        System.out.println("컨슈머 : " + message);
        FrontDto frontDto = objectMapper.readValue(message, FrontDto.class);
        matchService.createMatches(frontDto);
    }

}
