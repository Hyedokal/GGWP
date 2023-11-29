package com.ggwp.searchservice.summoner.feign;

import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lol-summoner", url = "https://kr.api.riotgames.com/lol/")
public interface LOLToSummonerFeign {
    @GetMapping("summoner/v4/summoners/by-puuid/{puuid}")
    ResponseGetSummonerDto getSummonerByPuuid(
            @PathVariable String puuid,
            @RequestParam("api_key") String apiKey
    );
}
