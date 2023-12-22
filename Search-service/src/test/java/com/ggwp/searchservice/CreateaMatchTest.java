//package com.ggwp.searchservice;
//
//import com.ggwp.searchservice.common.dto.FrontDto;
//import com.ggwp.searchservice.match.MatchRepository.MatchSummonerRepository;
//import com.ggwp.searchservice.match.service.MatchService;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class CreateaMatchTest {
//    @Autowired
//    private MatchService matchService;
//
//    @Autowired
//    private MatchSummonerRepository matchSummonerRepository;
//
//    @BeforeEach
//    public void beforedbreset() {
//        matchSummonerRepository.deleteAll();
//    }
//
//    @AfterEach
//    public void dbreset() {
//        matchSummonerRepository.deleteAll();
//    }
//
//    @Test
//    @Commit
//    @Order(1)
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
//
//    @Test
//    @Commit
//    @Order(2)
//    public void API_반복횟수점검() throws Exception {
//        List<FrontDto> frontdtoList = new ArrayList<>();
//
//        FrontDto ex1 = FrontDto.builder()
//                .tagLine("KR1")
//                .gameName("엘리스바이")
//                .build();
//
//        matchService.createMatches(ex1);
//
//        assertEquals(100, matchSummonerRepository.count());
//    }
//
//    @Test
//    @Order(3)
//    public void API_반복2횟수점검() throws Exception {
//        List<FrontDto> frontdtoList = new ArrayList<>();
//
//        FrontDto ex1 = FrontDto.builder()
//                .tagLine("KR1")
//                .gameName("엘리스바이")
//                .build();
//
//        matchService.createMatches(ex1);
//
//        assertEquals(150, matchSummonerRepository.count());
//    }
//}
