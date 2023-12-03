package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.dto.MatchDto;

import java.util.List;

public interface MatchService {
    List<String> getMatchIdsToFeign(TokenDto tokenDto);

    MatchDto getMatchToFegin(String matchId);

    Match matchToEntity(MatchDto matchDto);

    ResponseDto<String> createMatch(String matchId);

    List<MatchDto> getMatchList(TokenDto tokenDto);


}
