package com.ggwp.searchservice.summoner.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.ResponseSummonerDto;

public interface SummonerService {
    ResponseDto<ResponseSummonerDto> getSummoner(TokenDto tokenDto); // DB 조회하여 소환사 정보 가져오기

    Summoner findSummoner(Account account); // Optional<Summoner> 찾기
}
