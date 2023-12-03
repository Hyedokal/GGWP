package com.ggwp.searchservice.league.controller;


import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.league.dto.ResponseLeagueDto;
import com.ggwp.searchservice.league.service.LeagueServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/league")
@RequiredArgsConstructor
@Tag(name = "League", description = "League API")
public class LeagueController {

    private final LeagueServiceImpl leagueService;

    @PostMapping("/get")
    @Operation(summary = "League (랭크 조회)", description = "DB로 조회")
    public ResponseDto<List<ResponseLeagueDto>> getLeague(@Valid @RequestBody TokenDto tokenDto) {
        return leagueService.getLeague(tokenDto);
    }

}
