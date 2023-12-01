package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.account.feign.LOLToAccountFeign;
import com.ggwp.searchservice.account.repository.AccountRepository;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.summoner.domain.Summoner;
import com.ggwp.searchservice.summoner.repository.SummonerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.ggwp.searchservice.common.exception.ErrorCode.AccountAfterSummonerCreation;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LOLToAccountFeign accountFeign;
    private final SummonerRepository summonerRepository;

    @Value("${LOL.apikey}")
    private String apiKey;

    @Override
    public Account findAccount(TokenDto tokenDto) {
        return accountRepository.findByGameNameAndTagLine(tokenDto.getGameName(), tokenDto.getTagLine())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFindAccount));
    }

    @Override
    public ResponseDto<String> createAccount(TokenDto tokenDto) { // api 호출

        ResponseAccountDto response = feignAccount(tokenDto);

        if (summonerRepository.existsByPuuid(response.getPuuid())) { // 소환사가 존재한다면
            Summoner summoner = summonerRepository.findSummonerByPuuid(response.getPuuid());
            accountRepository.save(response.toEntity(summoner));
        } else {
            throw new CustomException(AccountAfterSummonerCreation);
        }
        return ResponseDto.success("Account 생성 완료");
    }

    @Override
    public ResponseDto<ResponseAccountDto> getAccount(TokenDto tokenDto) { // DB 조회
        Account account = findAccount(tokenDto);
        return ResponseDto.success(account.toDto());
    }

    public ResponseAccountDto feignAccount(TokenDto tokenDto) {
        return accountFeign.getAccount(
                tokenDto.getGameName(),
                tokenDto.getTagLine(),
                apiKey).orElseThrow(() -> new CustomException(ErrorCode.NotFeignAccount));
    }
}

