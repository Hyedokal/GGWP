package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.CreateAccountDto;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.common.dto.FrontDto;

public interface AccountService {

    ResponseAccountDto updateAccount(CreateAccountDto createAccountDto);

    String existAccount(FrontDto frontDto);

    void createAccount(CreateAccountDto createAccountDto);

    Account findAccount(FrontDto frontDto);

    ResponseAccountDto getAccount(FrontDto frontDto);
}