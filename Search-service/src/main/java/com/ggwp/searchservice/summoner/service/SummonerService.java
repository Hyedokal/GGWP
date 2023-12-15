package com.ggwp.searchservice.summoner.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.CreateSummonerDto;
import com.ggwp.searchservice.summoner.dto.ResponseSummonerDto;

public interface SummonerService {
    ResponseSummonerDto getSummoner(FrontDto frontDto); // DB 조회하여 소환사 정보 가져오기

    Summoner findSummoner(Account account); // Optional<Summoner> 찾기

    boolean existSummoner(CreateSummonerDto createSummonerDto);

    Summoner findSummonerByPuuid(CreateSummonerDto createSummonerDto);

    void saveSummoner(Summoner summoner);

    ResponseSummonerDto summonerFeign(String puuid, String apiKey);

}
