package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.CreateAccountDto;
import com.ggwp.searchservice.account.dto.FeignAccountDto;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.account.feign.LOLToAccountFeign;
import com.ggwp.searchservice.account.repository.AccountRepository;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import com.ggwp.searchservice.summoner.domain.Summoner;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LOLToAccountFeign accountFeign;

    @Value("${LOL.apikey}")
    private String apiKey;

    // Account 얻기
    public ResponseAccountDto getAccount(TokenDto tokenDto) {
        return toDto(findAccount(tokenDto));
    }

    public Account findAccount(TokenDto tokenDto) { // Account 엔티티 찾기
        return accountRepository.findByGameNameAndTagLine(tokenDto.getGameName(), tokenDto.getTagLine())
                .orElseThrow(() -> new CustomException(ErrorCode.NotFindAccount));
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Account createAccount(CreateAccountDto createAccountDto, Summoner summoner) {
        Account account = Account.builder()
                .puuid(createAccountDto.getPuuid())
                .gameName(createAccountDto.getGameName())
                .tagLine(createAccountDto.getTagLine())
                .summoner(summoner)
                .build();
        accountRepository.save(account);
        System.out.println("Account 생성!");
        return account;
    }

    public FeignAccountDto feignAccount(TokenDto tokenDto) { // 롤 API Feign
        return accountFeign.getAccount(
                tokenDto.getGameName(),
                tokenDto.getTagLine(),
                apiKey).orElseThrow(() -> new CustomException(ErrorCode.NotFeignAccount));
    }

    public ResponseAccountDto toDto(Account account) { // Dto로 변환
        return ResponseAccountDto.builder()
                .puuid(account.getPuuid())
                .gameName(account.getGameName())
                .tagLine(account.getTagLine())
                .build();
    }

    public Account toEntity(CreateAccountDto createAccountDto, Summoner summoner) { // 엔티티로 변환
        return Account.builder()
                .puuid(createAccountDto.getPuuid())
                .gameName(createAccountDto.getGameName())
                .tagLine(createAccountDto.getTagLine())
                .summoner(summoner)
                .build();
    }

    @Override
    public String existAccount(TokenDto tokenDto) {
        if (accountRepository.existsByGameNameAndTagLine(tokenDto.getGameName(), tokenDto.getTagLine())) {
            return findAccount(tokenDto).getPuuid();
        } else { // DB에 없다면 롤 API로 Account를 feign으로 가져온다.
            return feignAccount(tokenDto).getPuuid();
        }
    }
}

