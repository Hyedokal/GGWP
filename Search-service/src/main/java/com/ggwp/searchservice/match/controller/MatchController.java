package com.ggwp.searchservice.match.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.dto.RequestPageDto;
import com.ggwp.searchservice.match.service.MatchServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search-service/v1/match")
@RequiredArgsConstructor
@Tag(name = "Match", description = "Match API")
public class MatchController {

    private final MatchServiceImpl matchService;

    @PostMapping("/create") // 매치 5개 api 불러와서 저장 (예시: 엘리스바이 KR1)
    @Operation(summary = "Match 생성", description = "매치에 관련된 1경기에 해당하는 소환사, 리그, 매치, 참가자, 팀 모두 저장됨 -> 5번 반복 -> 총 5경기 저장")
    public ResponseDto<String> createMatch(@Valid @RequestBody FrontDto frontDto) throws JsonProcessingException {
        matchService.sendMessage(frontDto);
//        matchService.createMatches(frontDto);
        return ResponseDto.success("Create Matches!");
    }

    @PostMapping("/get")
    @Operation(summary = "Match 조회", description = "소환사의 해당하는 경기 리스트 조회")
    public ResponseDto<List<MatchDto>> getMatchList(@Valid @RequestBody FrontDto frontDto) {
        return ResponseDto.success(matchService.getMatchList(frontDto));
    }

    @PostMapping("/get/pages") // 페이징 임시..
    @Operation(summary = "Match 페이징 처리 조회", description = "페이징 처리를 통해 소환사 경기 리스트 조회: 5개 조회")
    public ResponseDto<Page<MatchDto>> getMatchPages(@Valid @RequestBody FrontDto frontDto, RequestPageDto.Search pageDto) {
        return ResponseDto.success(matchService.pagedMatches(frontDto, pageDto));
    }

}
