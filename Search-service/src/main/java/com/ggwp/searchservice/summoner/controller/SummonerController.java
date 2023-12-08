package com.ggwp.searchservice.summoner.controller;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.summoner.dto.ResponseSummonerDto;
import com.ggwp.searchservice.summoner.service.SummonerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/summoner")
@Tag(name = "Summoner", description = "Summoner API")
public class SummonerController {

    private final SummonerService summonerService;

    @PostMapping("/get") // 롤 puuid로 Summoner 가져오기 No-API
    @Operation(summary = "Summoner 조회", description = "DB로 조회")
    public ResponseDto<ResponseSummonerDto> getSummoner(@Valid @RequestBody FrontDto frontDto) {
        return ResponseDto.success(summonerService.getSummoner(frontDto));
    }
}
