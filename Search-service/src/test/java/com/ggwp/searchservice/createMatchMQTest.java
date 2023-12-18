package com.ggwp.searchservice;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.league.repository.LeagueRepository;
import com.ggwp.searchservice.match.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest
public class createMatchMQTest {

    @Autowired
    private final MatchService matchService;

    @Autowired
    private final LeagueRepository leagueRepository;

    public createMatchMQTest(MatchService matchService, LeagueRepository leagueRepository) {
        this.matchService = matchService;
        this.leagueRepository = leagueRepository;
    }

    @Test
    public void API_호출횟수점검() throws Exception {
        List<FrontDto> frontdtoList = new ArrayList<>();

        FrontDto ex1 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("엘리스바이")
                .build();
        frontdtoList.add(ex1);
        FrontDto ex2 = FrontDto.builder()
                .tagLine("KR3")
                .gameName("괴물쥐")
                .build();
        frontdtoList.add(ex2);
        FrontDto ex3 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("Hide on Bush")
                .build();
        frontdtoList.add(ex3);
        FrontDto ex4 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("Akaps")
                .build();
        frontdtoList.add(ex4);
        FrontDto ex5 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("쌈오징어")
                .build();
        frontdtoList.add(ex5);

        int ThreadCount = frontdtoList.size();

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(ThreadCount);
        // when
        for (FrontDto frontDto : frontdtoList) {
            executorService.submit(() -> {
                try {
                    matchService.sendMessage(frontDto);
                } catch (Exception e) {

                } finally {
                    countDownLatch.countDown();
                }
            });
        }
        // then
        countDownLatch.await();

        Thread.sleep(10000);
        
    }
}
