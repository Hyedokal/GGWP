package com.ggwp.squadservice;

import com.ggwp.squadservice.dto.response.riotapi.LeagueEntryDTO;
import com.ggwp.squadservice.dto.response.riotapi.ResponseGetSummonerDto;
import com.ggwp.squadservice.enums.QType;
import com.ggwp.squadservice.feign.CommentFeignClient;
import com.ggwp.squadservice.feign.RiotFeignClient;
import com.ggwp.squadservice.service.SquadService;
import com.ggwp.squadservice.service.impl.SquadServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class SquadServiceImplApplicationTests {
    @Autowired
    private SquadService squadService;

    @Mock
    private RiotFeignClient riotFeignClient;
    private CommentFeignClient commentFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(SquadServiceImplApplicationTests.class);
    }



    @Test
    @DisplayName("티어 정보 저장 테스트")
    void testGetSummonerRank() {
        //given
        String summonerName = "혜지 도륙내는 칼";

        // Mock 객체의 행동 설정
        when(riotFeignClient.getSummonerId(eq(summonerName), anyString()))
                .thenReturn(new ResponseGetSummonerDto()); // 예제로 더미 객체 반환 설정

        Set<LeagueEntryDTO> rankInfo = new HashSet<>();
        rankInfo.add(new LeagueEntryDTO("RANKED_SOLO_5x5", "GOLD", "IV"));
        rankInfo.add(new LeagueEntryDTO("RANKED_FLEX_SR", "PLATINUM", "III"));

        when(riotFeignClient.getRankInfo(anyString(), anyString()))
                .thenReturn(rankInfo); // 예제로 더미 랭크 정보 반환 설정

        //when
        Map<QType, String> rankMap = squadService.getSummonerRank(summonerName);

        //then
        Assert.assertEquals(rankMap.get(QType.SOLO_RANK), "G4");
        Assert.assertEquals(rankMap.get(QType.FLEX_RANK), "P3");
    }

}
