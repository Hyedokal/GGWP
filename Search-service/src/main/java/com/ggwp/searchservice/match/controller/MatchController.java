package com.ggwp.searchservice.match.controller;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.dto.RequestPageDto;
import com.ggwp.searchservice.match.service.MatchServiceImpl;
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
public class MatchController {

    private final MatchServiceImpl matchService;

    @PostMapping("/create") // 매치 5개 api 불러와서 저장
    public ResponseDto<String> createMatch(@Valid @RequestBody FrontDto frontDto) throws InterruptedException {
        matchService.createMatches(frontDto);
        return ResponseDto.success("Create Matches!");
    }

    @PostMapping("/get")
    public ResponseDto<List<MatchDto>> getMatchList(@Valid @RequestBody FrontDto frontDto) {
        return ResponseDto.success(matchService.getMatchList(frontDto));
    }

    @PostMapping("/get/pages") // 페이징 임시..
    public ResponseDto<Page<MatchDto>> getMatchPages(@Valid @RequestBody FrontDto frontDto, RequestPageDto.Search pageDto) {
        return ResponseDto.success(matchService.pagedMatches(frontDto, pageDto));
    }

}
