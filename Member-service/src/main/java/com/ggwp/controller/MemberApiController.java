package com.ggwp.controller;

import com.ggwp.dto.RequestCreateMemberDto;
import com.ggwp.dto.ResponseDto;
import com.ggwp.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/v1/member")
@RequiredArgsConstructor
public class MemberApiController {

    private  final MemberService memberService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "server is available!";
    }
    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody RequestCreateMemberDto requestCreateMemberDto) {
        memberService.createUser(requestCreateMemberDto);
        log.info("뉴유저 signed up: {}", requestCreateMemberDto.getEmail());
        return ResponseEntity.ok(null);
    }

}