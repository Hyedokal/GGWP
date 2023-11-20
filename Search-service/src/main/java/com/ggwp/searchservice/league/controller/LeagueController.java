package com.ggwp.searchservice.league.controller;


import com.ggwp.searchservice.league.dto.ResponseFindLeagueDto;
import com.ggwp.searchservice.league.dto.ResponseGetLeagueDto;
import com.ggwp.searchservice.league.service.LeagueService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/league")
@RequiredArgsConstructor
public class LeagueController {

    private final LeagueService leagueService;

    @GetMapping("get/{summonerid}") // summoner_id
    @Operation(summary = "League (랭크 조회)", description = "encrypted된 id로 랭크 조회")
    public ResponseEntity<?> getLeagues(@PathVariable String summonerid){
       List<ResponseGetLeagueDto> leagueDto = leagueService.getLeaguesAndSave(summonerid);
        return ResponseEntity.ok(leagueDto);
    }

    @GetMapping("get/{summonerId}/no-api")
    @Operation(summary = "League (랭크 조회)", description = "DB로 조회")
    public ResponseEntity<?> getLeaguesNoApi(@PathVariable String summonerId){
        List<ResponseFindLeagueDto> leagueDto = leagueService.getLeaguesNoApi(summonerId);
        return ResponseEntity.ok(leagueDto);
    }

}
