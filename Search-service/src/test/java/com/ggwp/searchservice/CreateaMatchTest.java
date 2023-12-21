package com.ggwp.searchservice;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.match.MatchRepository.MatchSummonerRepository;
import com.ggwp.searchservice.match.service.MatchService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CreateaMatchTest {
    @Autowired
    private MatchService matchService;

    @Autowired
    private MatchSummonerRepository matchSummonerRepository;

    @AfterEach
    public void dbreset() {
        matchSummonerRepository.deleteAll();
    }

//    @Test
//    public void API_호출횟수점검() throws Exception {
//        List<FrontDto> frontdtoList = new ArrayList<>();
//
//        FrontDto ex1 = FrontDto.builder()
//                .tagLine("KR1")
//                .gameName("엘리스바이")
//                .build();
//
//        matchService.createMatches(ex1);
//
//        assertEquals(50, matchSummonerRepository.count());
//    }
}
