package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.match.MatchRepository.MatchSummonerRepository;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.domain.MatchSummoner;
import com.ggwp.searchservice.match.dto.MatchSummonerDto;
import com.ggwp.searchservice.summoner.domain.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MatchSummonerServiceImpl implements MatchSummonerService {

    private final MatchSummonerRepository matchSummonerRepository;

    // 소환사의 matchId들을 matchSummoner에서 가져오기
    @Transactional(readOnly = true)
    public List<MatchSummonerDto> getMatchSummonerBySummonerId(String summonerId) {
        List<MatchSummoner> matchSummoners = matchSummonerRepository.findBySummonerId(summonerId);

        List<MatchSummonerDto> matchSummonerDtoList = new ArrayList<>();

        for (MatchSummoner matchSummoner : matchSummoners) {
            MatchSummonerDto matchSummonerDto = matchSummonerToDto(matchSummoner);
            matchSummonerDtoList.add(matchSummonerDto);
        }
        return matchSummonerDtoList;
    }

    private MatchSummonerDto matchSummonerToDto(MatchSummoner matchSummoner) {
        return MatchSummonerDto.builder()
                .matchId(matchSummoner.getMatch().getMatchId())
                .summonerId(matchSummoner.getSummoner().getId())
                .build();
    }

    @Override
    public MatchSummoner createMatchSummoner(Match match, Summoner summoner) {
        MatchSummoner matchSummoner = MatchSummoner.builder()
                .match(match)
                .summoner(summoner)
                .build();
        matchSummonerRepository.save(matchSummoner);
        return matchSummoner;
    }

}
