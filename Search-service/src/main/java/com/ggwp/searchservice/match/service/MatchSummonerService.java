package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.MatchSummoner;
import com.ggwp.searchservice.match.dto.MatchSummonerDto;
import com.ggwp.searchservice.summoner.domain.Summoner;

import java.util.List;

public interface MatchSummonerService {

    List<MatchSummonerDto> getMatchSummonerBySummonerId(String summonerId);

    MatchSummonerDto toDto(MatchSummoner matchSummoner);

    MatchSummoner createMatchSummoner(Match match, Summoner summoner);
}
