package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.CreateAccountDto;
import com.ggwp.searchservice.account.dto.FeignAccountDto;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import com.ggwp.searchservice.summoner.domain.Summoner;

public interface AccountService {

    ResponseAccountDto getAccount(TokenDto tokenDto);

    Account findAccount(TokenDto tokenDto);

    Account createAccount(CreateAccountDto createAccountDto, Summoner summoner);

    FeignAccountDto feignAccount(TokenDto tokenDto);

    ResponseAccountDto toDto(Account account);

    Account toEntity(CreateAccountDto createAccountDto, Summoner summoner);

    String existAccount(TokenDto tokenDto);

}