package com.ggwp.searchservice.match.feign;


import com.ggwp.searchservice.match.dto.MatchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "lol-match", url = "https://asia.api.riotgames.com/lol/")
public interface LoLToMatchFeign {

    @GetMapping("match/v5/matches/{matchId}")
    Optional<MatchDto> getMatch(
            @PathVariable("matchId") String matchId,
            @RequestParam("api_key") String apiKey
    );

    @GetMapping("match/v5/matches/by-puuid/{puuid}/ids?start=0&count=5")
    Optional<List<String>> getMatchIds(
            @PathVariable("puuid") String puuid,
            @RequestParam("api_key") String apiKey
    );

}

