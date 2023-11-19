package com.ggwp.searchservice.summoner.feign;

import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "lol-summoner", url = "https://kr.api.riotgames.com/lol/")
public interface LOLToSummonerFeign {
    // api key를 이용하여 소환사의 정보 가져오기
    @GetMapping("summoner/v4/summoners/by-name/{summonerName}")
    ResponseGetSummonerDto getSummoner(
            @PathVariable String summonerName,
            @RequestParam("api_key") String apiKey
    );
}
