package com.ggwp.searchservice;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class createMatchMQTest {

    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchRepository matchRepository;

//    @AfterEach
//    public void dbreset() {
//        matchRepository.deleteAll();
//    }

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
                .gameName("Hide on bush")
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
        FrontDto ex6 = FrontDto.builder()
                .tagLine("1233")
                .gameName("바보야")
                .build();
        frontdtoList.add(ex6);
        FrontDto ex7 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("개발자")
                .build();
        frontdtoList.add(ex7);
        FrontDto ex8 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("dlwlrma22")
                .build();
        frontdtoList.add(ex8);
        FrontDto ex9 = FrontDto.builder()
                .tagLine("0303")
                .gameName("허거덩")
                .build();
        frontdtoList.add(ex9);
        FrontDto ex10 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("Radiohead")
                .build();
        frontdtoList.add(ex10);
        FrontDto ex11 = FrontDto.builder()
                .tagLine("7267")
                .gameName("DRX")
                .build();
        frontdtoList.add(ex11);
        FrontDto ex12 = FrontDto.builder()
                .tagLine("KR1")
                .gameName("KJMU")
                .build();
        frontdtoList.add(ex12);
        FrontDto ex13 = FrontDto.builder()
                .tagLine("0828")
                .gameName("정지훈")
                .build();
        frontdtoList.add(ex13);

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


        Thread.sleep(60000);

        assertEquals(65, matchRepository.count());
    }
}
