package com.ggwp.memberservice.controller;


import com.ggwp.memberservice.dto.request.user.PatchLolNickNameRequestDto;
import com.ggwp.memberservice.dto.response.user.GetSignInUserResponseDto;
import com.ggwp.memberservice.dto.response.user.GetUserResponseDto;
import com.ggwp.memberservice.dto.response.user.PatchLolNickNameResponseDto;
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

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Member Service is Healthy");
    }

    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(@AuthenticationPrincipal String uuid) {
        ResponseEntity<? super GetSignInUserResponseDto> response = memberService.getSignInUser(uuid);
        return response;
    }


        @GetMapping("/userInfo")
        public ResponseEntity<? super GetUserResponseDto> getUser(
                @AuthenticationPrincipal String uuid
        ) {
            ResponseEntity<? super GetUserResponseDto> response = memberService.getUser(uuid);
            return response;
        }



    @PatchMapping("/lolNickname")
    public ResponseEntity<? super PatchLolNickNameResponseDto> patchLolNickName(
            @RequestBody @Valid PatchLolNickNameRequestDto requestBody,
            @AuthenticationPrincipal String uuid
            ){
        ResponseEntity<? super PatchLolNickNameResponseDto> response = memberService.patchLolNickName(requestBody, uuid);
        return response;
    }

}

