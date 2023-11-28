package com.ggwp.searchservice.summoner.service;

import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.ResponseGetSummonerDto;
import com.ggwp.searchservice.summoner.feign.LOLToSummonerFeign;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerService {
    private final SummonerRepository summonerRepository;
    private final LOLToSummonerFeign summonerFeign;
    @Value("${LOL.apikey}")
    private String apiKey;

    // 롤 api를 통해서 소환사 정보 가져오기 및 DB 저장
    public ResponseGetSummonerDto getSummonerAndSave(String name) {
        ResponseGetSummonerDto summonerDto = summonerFeign.getSummoner(name, apiKey);
        Summoner summoner = summonerDto.toEntity();
        summonerRepository.save(summoner);
        return summonerDto;
    }

    // DB에 저장한 소환사 정보 가져오기
    public ResponseGetSummonerDto getSummonerNoApi(String name) {
        Summoner summoner = summonerRepository.findSummonerByName(name);
        ResponseGetSummonerDto summonerDto = summoner.toDto(summoner);
        return summonerDto;
    }

}
