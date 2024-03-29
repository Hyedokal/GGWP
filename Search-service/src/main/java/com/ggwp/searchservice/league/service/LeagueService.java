package com.ggwp.searchservice.league.service;

import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.league.dto.ResponseLeagueDto;
import com.ggwp.searchservice.summoner.domain.Summoner;

import java.util.List;

public interface LeagueService {
    void createLeague(String summonerId);

    void updateLeagues(String summonerId);

    boolean existLeague(String summonerId);

    List<ResponseLeagueDto> getLeague(FrontDto frontDto);
}
