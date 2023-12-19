package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.dto.RequestPageDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MatchService {

    void createMatches(FrontDto frontDto);

    List<MatchDto> getMatchList(FrontDto frontDto);

    Page<MatchDto> pagedMatches(FrontDto frontDto, RequestPageDto.Search dto);


}
