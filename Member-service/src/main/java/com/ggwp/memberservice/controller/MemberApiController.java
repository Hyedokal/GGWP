package com.ggwp.memberservice.controller;


import com.ggwp.memberservice.dto.request.user.PatchEmailRequestDto;
import com.ggwp.memberservice.dto.response.user.GetSignInUserResponseDto;
import com.ggwp.memberservice.dto.response.user.GetUserResponseDto;
import com.ggwp.memberservice.dto.response.user.PatchEmailResponseDto;
import com.ggwp.memberservice.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member-service/v1/member")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(@AuthenticationPrincipal String email) {
        ResponseEntity<? super GetSignInUserResponseDto> response = memberService.getSignInUser(email);
        return response;
    }


    @GetMapping("{email}")
    public ResponseEntity<? super GetUserResponseDto> getUser(
            @PathVariable("email") String email
    ) {
        ResponseEntity<? super GetUserResponseDto> response = memberService.getUser(email);
        return response;
    }



    @PatchMapping("/email")
    public ResponseEntity<? super PatchEmailResponseDto> patchEmail(
            @RequestBody @Valid PatchEmailRequestDto requestBody,
            @AuthenticationPrincipal String email
            ){
        ResponseEntity<? super PatchEmailResponseDto> response = memberService.patchEmail(requestBody, email);
        return response;
    }

}

