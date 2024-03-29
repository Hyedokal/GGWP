package com.ggwp.searchservice.league.feign;


import com.ggwp.searchservice.league.dto.CreateLeagueDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "lol-league", url = "https://kr.api.riotgames.com/lol/")
public interface LoLToLeagueFeign {

    @GetMapping("league/v4/entries/by-summoner/{encryptedSummonerId}")
    Optional<List<CreateLeagueDto>> getLeagues(
            @PathVariable("encryptedSummonerId") String encryptedSummonerId,
            @RequestParam("api_key") String apiKey
    );
}

