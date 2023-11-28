package com.ggwp.searchservice.account.controller;

import com.ggwp.searchservice.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/{lol_name}/{tagLine}")
    public ResponseEntity<?> createAccount(@PathVariable String lol_name, @PathVariable String tagLine) { // 토큰에서 닉네임과 태그를 받는다. (토큰 받으면 수정)
        accountService.createAccount(lol_name, tagLine);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PostMapping("/{lol_name}/{tagLine}/getAccount")
    public ResponseEntity<?> getAccount(@PathVariable String lol_name, @PathVariable String tagLine) { // DB에서 조회
        return ResponseEntity.ok(accountService.getAccount(lol_name, tagLine));
    }
}
