package com.ggwp.memberservice.controller;


import com.ggwp.memberservice.dto.request.user.PatchLolNickNameRequestDto;
import com.ggwp.memberservice.dto.request.user.PatchTag;
import com.ggwp.memberservice.dto.request.user.PersonalitiesRequestDto;
import com.ggwp.memberservice.dto.response.user.*;
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

        @GetMapping("match/list")
        public ResponseEntity<? super GetMatchInfoResponseDto> getMatchList(
                @AuthenticationPrincipal String uuid
        ) {
            ResponseEntity<? super GetMatchInfoResponseDto> response = memberService.getMatchInfo(uuid);
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

    //tag를 변경하는 메서드
    @PatchMapping("/tag")
    public ResponseEntity<String> patchTag(@RequestBody PatchTag patchTagDto, @AuthenticationPrincipal String uuid) {

        memberService.patchTag(uuid, patchTagDto);
        return ResponseEntity.ok().body("Modified Successfully");

    }
        @PostMapping("/personalities")
    public ResponseEntity<? super PersonalitiesResponseDto> addMemberPersonalities(
            @RequestBody @Valid PersonalitiesRequestDto requestBody,
            @AuthenticationPrincipal String uuid
    ){
        ResponseEntity<? super PersonalitiesResponseDto> response = memberService.addPersonalities(uuid, requestBody);
        return response;
    }


    @GetMapping("/personalities")
    public ResponseEntity<? super PersonalitiesInfoResponseDto> getPersonalities(@AuthenticationPrincipal String uuid) {
        return memberService.getPersonalities(uuid);
    }



    // squad-service에서

}

