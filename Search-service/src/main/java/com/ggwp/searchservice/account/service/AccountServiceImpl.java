package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.CreateAccountDto;
import com.ggwp.searchservice.account.dto.FeignAccountDto;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.account.feign.LOLToAccountFeign;
import com.ggwp.searchservice.account.repository.AccountRepository;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.common.exception.CustomException;
import com.ggwp.searchservice.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final LOLToAccountFeign accountFeign;

    @Value("${LOL.apikey}")
    private String apiKey;


    // Account 얻기
    @Transactional(readOnly = true)
    @Override
    public ResponseAccountDto getAccount(FrontDto frontDto) {
        return acccountToDto(findAccount(frontDto));
    }

    @Override
    public Account findAccount(FrontDto frontDto) { // Optional Account 엔티티 찾기
        return accountRepository.findAccountByGameNameAndTagLine(frontDto.getGameName(), frontDto.getTagLine())
                .orElseThrow(() -> new CustomException.NotFoundAccountException(frontDto, ErrorCode.NotFindAccount));
    }


    @Override
    @Transactional
    public void createAccount(CreateAccountDto createAccountDto) {
        Account account = accountToEntity(createAccountDto);
        accountRepository.save(account);
    }

    private FeignAccountDto feignAccountByNameAndTag(FrontDto frontDto) { // 롤 API Feign
        return accountFeign.getAccount(
                frontDto.getGameName(),
                frontDto.getTagLine(),
                apiKey).orElseThrow(() -> new CustomException(ErrorCode.NotfeignAccountByNameAndTag));
    }

    private Account feignAccountToEntity(FeignAccountDto accountDto) { // 롤 API Feign
        return Account.builder()
                .puuid(accountDto.getPuuid())
                .gameName(accountDto.getGameName())
                .tagLine(accountDto.getTagLine())
                .build();
    }

    private ResponseAccountDto acccountToDto(Account account) { // Dto로 변환
        return ResponseAccountDto.builder()
                .puuid(account.getPuuid())
                .gameName(account.getGameName())
                .tagLine(account.getTagLine())
                .build();
    }


    private Account accountToEntity(CreateAccountDto createAccountDto) { // 엔티티로 변환
        return Account.builder()
                .puuid(createAccountDto.getPuuid())
                .gameName(createAccountDto.getGameName())
                .tagLine(createAccountDto.getTagLine())
                .build();
    }

    private Account findAccountByPuuid(CreateAccountDto createAccountDto) {
        return accountRepository.findAccountByPuuid(createAccountDto.getPuuid());
    }


    @Override
    @Transactional
    public String existAccount(FrontDto frontDto) {
        if (accountRepository.existsByGameNameAndTagLine(frontDto.getGameName(), frontDto.getTagLine())) {
            return findAccount(frontDto).getPuuid();
        } else { // DB에 없다면 롤 API로 Account를 feign으로 가져온다.
            return feignAccountByNameAndTag(frontDto).getPuuid();
        }
    }

    public Account responseToEntity(ResponseAccountDto accountDto) {
        return Account.builder()
                .gameName(accountDto.getGameName())
                .puuid(accountDto.getPuuid())
                .tagLine(accountDto.getTagLine())
                .build();
    }

    @Override
    @Transactional
    public ResponseAccountDto updateAccount(CreateAccountDto createAccountDto) {
        Account account = findAccountByPuuid(createAccountDto);
        account.updateAccount(createAccountDto);
        accountRepository.save(account);
        return acccountToDto(account);
    }

}

