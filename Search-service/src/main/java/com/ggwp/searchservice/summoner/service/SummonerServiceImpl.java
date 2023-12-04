package com.ggwp.searchservice.summoner.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.dto.CreateSummonerDto;
import com.ggwp.searchservice.summoner.dto.ResponseSummonerDto;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SummonerServiceImpl implements SummonerService {

    private final SummonerRepository summonerRepository;
    private final AccountService accountService;

    @Override
    public Summoner findSummoner(Account account) { // Optional을 통해 Summoner 가져오기
        return summonerRepository.findByPuuid(account.getPuuid())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFindSummoner));
    }

    @Override  //DB에 저장한 소환사 정보 가져오기 No-API
    public ResponseDto<ResponseSummonerDto> getSummoner(TokenDto tokenDto) {
        Account account = accountService.findAccount(tokenDto);
        Summoner summoner = findSummoner(account);
        return ResponseDto.success(summoner.toDto(summoner));
    }

    public boolean existSummoner(CreateSummonerDto createSummonerDto) {
        return summonerRepository.existsByPuuid(createSummonerDto.getPuuid());
    }

    @Override
    public Summoner findSummonerByPuuid(CreateSummonerDto createSummonerDto) {
        return summonerRepository.findSummonerByPuuid(createSummonerDto.getPuuid());
    }

    @Override
    public void saveSummoner(Summoner summoner) {
        summonerRepository.save(summoner);
    }

}
