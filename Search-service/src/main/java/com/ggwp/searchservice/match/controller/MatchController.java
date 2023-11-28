package com.ggwp.searchservice.match.controller;

import com.ggwp.searchservice.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping("/matchlist/{puuid}/save") // 매치 5개 api 불러와서 저장
    public ResponseEntity<?> createMatch(@PathVariable String puuid) throws InterruptedException {
        matchService.createMatch(puuid);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/matchlist/{summonerId}/get")
    public ResponseEntity<?> getMatchList(@PathVariable String summonerId) {
        return ResponseEntity.ok(matchService.getMatchList(summonerId));
    }
}
