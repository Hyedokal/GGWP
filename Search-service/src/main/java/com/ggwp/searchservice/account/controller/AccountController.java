package com.ggwp.searchservice.account.controller;

import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.ResponseDto;
import com.ggwp.searchservice.common.dto.TokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/account")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Account API")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    @Operation(summary = "Account 생성", description = "lol api 호출")
    public ResponseDto<String> createAccount(@Valid @RequestBody TokenDto tokenDto) { // 토큰에서 닉네임과 태그를 받는다. (토큰 받으면 수정)
        return accountService.createAccount(tokenDto);
    }

    @PostMapping("/get")
    @Operation(summary = "Account 조회", description = "DB에서 조회")
    public ResponseDto<ResponseAccountDto> getAccount(@Valid @RequestBody TokenDto tokenDto) { // DB에서 조회
        return accountService.getAccount(tokenDto);
    }
}
