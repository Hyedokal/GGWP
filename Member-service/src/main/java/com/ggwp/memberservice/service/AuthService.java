package com.ggwp.memberservice.service;


import com.ggwp.memberservice.dto.request.auth.SignInRequestDto;
import com.ggwp.memberservice.dto.request.auth.SignUpRequestDto;
import com.ggwp.memberservice.dto.response.auth.SignInResponseDto;
import com.ggwp.memberservice.dto.response.auth.SignUpResponseDto;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto signUpRequestDto);

    //로그인
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto signInRequestDto);
}
