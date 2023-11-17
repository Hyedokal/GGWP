package com.ggwp.memberservice.controller;

import com.ggwp.memberservice.dto.RequestCreateMemberDto;
import com.ggwp.memberservice.dto.ResponseDto;
import com.ggwp.memberservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberApiController {

    private  final MemberService memberService;

    @GetMapping("health-check")
    public String healthCheck(){
        return "server is available!";
    }



    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signUp(@RequestBody RequestCreateMemberDto requestCreateMemberDto) {
        try {
            memberService.createUser(requestCreateMemberDto);
            ResponseDto responseDto = new ResponseDto("success", "회원가입 성공", null);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            ResponseDto responseDto = new ResponseDto("error", "회원가입 실패",null);
            return ResponseEntity.badRequest().body(responseDto);
        }
    }

}