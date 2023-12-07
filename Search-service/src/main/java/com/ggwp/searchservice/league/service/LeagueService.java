package com.ggwp.searchservice.league.service;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.league.dto.ResponseLeagueDto;
import com.ggwp.searchservice.summoner.domain.Summoner;

import java.util.List;

public interface LeagueService {
    List<League> createLeague(Summoner summoner);

    void updateLeagues(Summoner summoner);

    List<ResponseLeagueDto> getLeague(FrontDto frontDto);
}
