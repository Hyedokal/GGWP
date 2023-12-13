package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.CreateAccountDto;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.summoner.domain.Summoner;

public interface AccountService {

    ResponseAccountDto updateAccount(CreateAccountDto createAccountDto);

    String existAccount(FrontDto frontDto);

    ResponseAccountDto createAccount(CreateAccountDto createAccountDto, Summoner summoner);

    Account findAccount(FrontDto frontDto);

    ResponseAccountDto getAccount(FrontDto frontDto);

    Account responseToEntity(ResponseAccountDto accountDto);

}