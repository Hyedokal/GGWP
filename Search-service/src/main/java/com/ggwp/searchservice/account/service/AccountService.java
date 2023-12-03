package com.ggwp.searchservice.account.service;

import com.ggwp.searchservice.account.domain.Account;
import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;

public interface AccountService {

    ResponseDto<String> createAccount(TokenDto tokenDto); // Account 생성

    ResponseDto<ResponseAccountDto> getAccount(TokenDto tokenDto); // Account DB 조회

    Account findAccount(TokenDto tokenDto); // 토큰으로 Account 찾기
}
