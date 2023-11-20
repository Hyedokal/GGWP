package com.ggwp.searchservice.match.controller;

import com.ggwp.searchservice.match.service.MatchService;
import lombok.RequiredArgsConstructor;
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

//    @GetMapping("get/match-id/{puuid}") 매치 0~5 가져오기
//    public ResponseEntity<?> getMatchId(@PathVariable String puuid){
//        List<String> list = matchService.getMatch(puuid);
//        return ResponseEntity.ok(list);
//    }

    @GetMapping("get/match/{matchId}") // 매치 1개 api 불러와서 저장
    public ResponseEntity<?> getMatch(@PathVariable String matchId){
        System.out.println("matchId = " + matchId);

        return ResponseEntity.ok(matchService.getMatch(matchId));
    }
}
