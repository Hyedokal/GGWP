package com.ggwp.searchservice.match.service;

import com.ggwp.searchservice.match.MatchRepository.MatchRepository;
import com.ggwp.searchservice.match.domain.Match;
import com.ggwp.searchservice.match.dto.MatchDto;
import com.ggwp.searchservice.match.feign.LoLToMatchFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Value("${LOL.apikey}")
    private String apiKey;

    private final LoLToMatchFeign matchFeign;

    private final MatchRepository matchRepository;
    public MatchDto getMatch(String matchId) {

           MatchDto matchDto = matchFeign.getMatch(matchId, apiKey);
           Match match = matchDto.toEntity();
           matchRepository.save(match);
            return matchDto;
    }

}
