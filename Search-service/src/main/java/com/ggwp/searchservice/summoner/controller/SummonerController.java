package com.ggwp.searchservice.summoner.controller;

import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/summoner")
public class SummonerController {

    private final SummonerService summonerService;

    @GetMapping("/get/{puuid}") // 롤 닉네임으로 Summoner 가져오기 USE API
    public ResponseEntity<?> getSummoner(@PathVariable String puuid) {
        ResponseGetSummonerDto summonerDto = summonerService.getSummonerAndSave(puuid);
        return ResponseEntity.ok(summonerDto);
    }

    @GetMapping("/get/{name}/no-api") // 롤 닉네임으로 Summoner 가져오기 No-API
    public ResponseEntity<?> getSummonerNoApi(@PathVariable String puuid) {
        ResponseGetSummonerDto summonerDto = summonerService.getSummonerNoApi(puuid);
        return ResponseEntity.ok(summonerDto);
    }
}
