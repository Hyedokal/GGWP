package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.MatchRepository.MatchSummonerRepository;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.Participant;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.feign.LoLToMatchFeign;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.RequestCreateSummonerDto;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import com.ggwp.searchservice.summoner.service.SummonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final LoLToMatchFeign matchFeign;
    private final MatchRepository matchRepository;
    private final SummonerRepository summonerRepository;
    private final SummonerService summonerService;
    private final MatchSummonerRepository matchSummonerRepository;
    
    @Value("${LOL.apikey}")
    private String apiKey;

    public void createMatch(String puuid) {

        List<String> matchIds = matchFeign.getMatchIds(puuid, apiKey);

        for (String matchId : matchIds) {

            MatchDto matchDto = matchFeign.getMatch(matchId, apiKey);
            Match match = matchDto.toEntity();

            List<Summoner> summoners = new ArrayList<>();

            List<Participant> participants = matchDto.getParticipantEntities();

            for (Participant participant : participants) {
                Summoner summoner = summonerRepository.findSummonerById(participant.getSummonerId());
                if (summoner == null) {
                    RequestCreateSummonerDto summonerDto = participant.createSummoner();
                    summoner = summonerService.createSummoner(summonerDto);
                }
                summoners.add(summoner);
            }
            match.addSummoners(summoners);

            matchRepository.save(match);

            for (Summoner summoner : summoners) {
                summoner.addMatch(match);
                summonerRepository.save(summoner);
            }

        }

    }
}
