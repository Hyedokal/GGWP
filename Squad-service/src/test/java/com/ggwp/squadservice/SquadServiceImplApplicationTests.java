package com.ggwp.squadservice;

import com.ggwp.squadservice.dto.response.riotapi.LeagueEntryDTO;
import com.ggwp.squadservice.dto.response.riotapi.ResponseGetSummonerDto;
import com.ggwp.squadservice.feign.RiotFeignClient;
import com.ggwp.squadservice.service.SquadService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@ComponentScan("com.ggwp.squadservice.feign")
class SquadServiceImplApplicationTests {
    @Autowired
    private SquadService squadService;

    @Mock
    private RiotFeignClient riotFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(SquadServiceImplApplicationTests.class);
    }


    @Test
    @DisplayName("티어 정보 저장 테스트")
    void testGetSummonerRank() {
        //given
        String summonerName = "혜지 도륙내는 칼";
        String apiKey = "RGAPI-edb61b90-0573-4f94-baa4-ddbdf0ddd58e";
        // Mock 객체의 행동 설정
        when(riotFeignClient.getSummonerId(summonerName, apiKey))
                .thenReturn(new ResponseGetSummonerDto()); // 예제로 더미 객체 반환 설정
        ResponseGetSummonerDto responseDto = riotFeignClient.getSummonerId(summonerName, apiKey);
        String encrypytedId = responseDto.getId();

        Set<LeagueEntryDTO> rankInfo = new HashSet<>();
        rankInfo.add(new LeagueEntryDTO("RANKED_SOLO_5x5", "GOLD", "IV"));
        rankInfo.add(new LeagueEntryDTO("RANKED_FLEX_SR", "PLATINUM", "III"));

        //when
        Set<LeagueEntryDTO> Test = riotFeignClient.getRankInfo(encrypytedId, apiKey);

        //then
        Assertions.assertEquals(rankInfo, Test);
    }

}
