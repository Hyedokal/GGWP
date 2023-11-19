package com.ggwp.searchservice.match.feign;


import com.ggwp.searchservice.match.dto.MatchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lol-match", url = "https://asia.api.riotgames.com/lol/")
public interface LoLToMatchFeign {

    @GetMapping("match/v5/matches/{matchId}")
    MatchDto getMatch(
            @PathVariable("matchId") String matchId,
            @RequestParam("api_key") String apiKey
    );
//    @GetMapping("match/v5/matches/by-puuid/{puuid}/ids")
//    List<String> getMatchId(
//            @PathVariable("puuid") String puuid,
//            @RequestParam("api_key") String apiKey
//    );


    // https://asia.api.riotgames.com/lol/match/v5/matches/KR_6679031558?api_key=RGAPI-4cac8adf-0eac-4db1-80ef-d799850cb1a7
}

