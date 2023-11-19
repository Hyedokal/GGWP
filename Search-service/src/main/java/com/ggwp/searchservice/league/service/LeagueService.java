package com.ggwp.searchservice.league.service;


import com.ggwp.searchservice.league.domain.League;
import com.ggwp.searchservice.league.dto.ResponseFindLeagueDto;
import com.ggwp.searchservice.league.dto.ResponseGetLeagueDto;
import com.ggwp.searchservice.league.feign.LoLToLeagueFeign;
import com.ggwp.searchservice.league.repository.LeagueRepository;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static com.ggwp.searchservice.league.dto.ResponseFindLeagueDto.toDto;

@Service
@RequiredArgsConstructor
public class LeagueService {

    @Value("${LOL.apikey}")
    private String apiKey;

    private final LeagueRepository leagueRepository;
    private final SummonerRepository summonerRepository;
    private final LoLToLeagueFeign leagueFeign;

    // 리그 정보 얻기 API 및 DB 저장
    public List<ResponseGetLeagueDto> getLeaguesAndSave(@PathVariable String summonerId){
        List<ResponseGetLeagueDto> leagueDto = leagueFeign.getLeagues(summonerId,apiKey);
        Summoner summoner = summonerRepository.findSummonerById(summonerId);
        List<League> leagueList = new ArrayList<>();
        for (ResponseGetLeagueDto dto : leagueDto) {

            leagueList.add(dto.toEntity(summoner));
        }
        leagueRepository.saveAll(leagueList);
        return leagueDto;
    }

    // 리그 정보 얻기 No - API
    public List<ResponseFindLeagueDto> getLeaguesNoApi(@PathVariable String summonerId) {
        List<League> leagueList = leagueRepository.findLeaguesBySummonerId(summonerId);
        List<ResponseFindLeagueDto> DtoList = new ArrayList<>();

        for (League league : leagueList) {
            ResponseFindLeagueDto leagueDto = toDto(league); // static
            DtoList.add(leagueDto);
        }
        return DtoList;
    }
}
