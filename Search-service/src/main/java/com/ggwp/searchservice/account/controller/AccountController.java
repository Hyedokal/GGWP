package com.ggwp.searchservice.account.controller;

import com.ggwp.searchservice.account.dto.ResponseAccountDto;
import com.ggwp.searchservice.account.service.AccountService;
import com.ggwp.searchservice.common.dto.FrontDto;
import com.ggwp.searchservice.common.dto.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/search-service/v1/account")
@RequiredArgsConstructor
@Tag(name = "Account", description = "Account API")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/health-check")
    public String healthcheck() {
        return "health-check";
    }

    @PostMapping("/get")
    @Operation(summary = "Account 조회", description = "DB에서 조회")
    public ResponseDto<ResponseAccountDto> getAccount(@Valid @RequestBody FrontDto frontDto) { // DB에서 조회
        return ResponseDto.success(accountService.getAccount(frontDto));
    }
}
