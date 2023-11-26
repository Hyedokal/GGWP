package com.ggwp.searchservice.match.controller;

import com.ggwp.searchservice.match.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @GetMapping("matchlist/{puuid}") // 매치 1개 api 불러와서 저장
    public ResponseEntity<?> getMatch(@PathVariable String puuid) {

        matchService.createMatch(puuid);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }
}
