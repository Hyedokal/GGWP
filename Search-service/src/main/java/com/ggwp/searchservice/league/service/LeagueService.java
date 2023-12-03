package com.ggwp.searchservice.league.service;

import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.league.dto.ResponseLeagueDto;
import com.ggwp.searchservice.summoner.domain.Summoner;

import java.util.List;

public interface LeagueService {
    ResponseDto<List<ResponseLeagueDto>> getLeague(TokenDto tokenDto); // 토큰으로 리그 얻기

    List<League> findLeagues(Summoner summoner); // 리그 엔티티 가져오기

    List<ResponseLeagueDto> leagueToDto(List<League> leagueList); // 리그 엔티티 -> DTO로 바꾸기
}
