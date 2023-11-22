package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.Participant;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.feign.LoLToMatchFeign;
import com.ggwp.searchservice.summoner.dto.RequestCreateSummonerDto;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Value("${LOL.apikey}")
    private String apiKey;

    private final LoLToMatchFeign matchFeign;

    private final MatchRepository matchRepository;

    private final SummonerService summonerService;

    public void createMatch(String matchId) {
        MatchDto matchDto = matchFeign.getMatch(matchId, apiKey);
        Match match = matchDto.toEntity();
        matchRepository.save(match);

        List<Participant> participants = matchDto.getParticipantEntities();

        for (Participant participant : participants) {
            RequestCreateSummonerDto summonerDto = participant.createSummoner();
            summonerService.createSummoner(summonerDto);
        }
    }

}
