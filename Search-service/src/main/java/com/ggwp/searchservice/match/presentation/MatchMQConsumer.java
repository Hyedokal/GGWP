package com.ggwp.searchservice.match.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.match.service.MatchService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class MatchMQConsumer {
    private final ObjectMapper objectMapper;
    private final MatchService matchService;
    private final RabbitTemplate rabbitTemplate;
    private final int maxRetryAttempts = 3;

    @RabbitListener(queues = "MATCH_QUEUE")
    public void createMatch(String message) throws JsonProcessingException, InterruptedException {
        // 큐의 크기가 최대 요청 수 이하일 때만 메서드 호출
        System.out.println("컨슈머: " + message);

        int retryAttempt;
        for (retryAttempt = 1; retryAttempt <= maxRetryAttempts; retryAttempt++) {
            try {
                FrontDto frontDto = objectMapper.readValue(message, FrontDto.class);
                matchService.createMatches(frontDto);
                // 성공하면 반복문 종료
                break;
            } catch (FeignException e) {
                if (e.status() == 429) {
                    System.out.println("API 호출 횟수 제한으로 1초 대기중.. (재시도 횟수: " + retryAttempt + ")");
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    // 다른 예외인 경우 출력
                    System.err.println("예외 발생: " + e.getMessage());
                    // 여기에서 예외를 다시 던져서 호출자에게 전파할 수도 있습니다.
                }
            }
        }

        // 최대 재시도 횟수를 초과하면 메서드 종료
        if (retryAttempt > maxRetryAttempts) {
            System.out.println("최대 재시도 횟수를 초과하였습니다.");
            rabbitTemplate.receiveAndConvert("MATCH_QUEUE");
        }
    }
}


