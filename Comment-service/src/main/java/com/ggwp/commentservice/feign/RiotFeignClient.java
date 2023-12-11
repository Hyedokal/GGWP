package com.ggwp.commentservice.feign;

import com.ggwp.commentservice.dto.response.riotapi.LeagueEntryDTO;
import com.ggwp.commentservice.dto.response.riotapi.ResponseGetSummonerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(name = "riotAPI", url = "https://kr.api.riotgames.com/lol/")
public interface RiotFeignClient {
    @GetMapping("summoner/v4/summoners/by-name/{summonerName}")
    ResponseGetSummonerDto getSummonerId(
            @PathVariable String summonerName,
            @RequestParam("api_key") String apiKey
    );

    @GetMapping("league/v4/entries/by-summoner/{encryptedSummonerId}")
    Set<LeagueEntryDTO> getRankInfo(@PathVariable String encryptedSummonerId,
                                    @RequestParam("api_key") String apiKey);
}
