package com.ggwp.memberservice.service;


import com.ggwp.memberservice.dto.request.auth.SignUpRequestDto;
import com.ggwp.memberservice.dto.response.ResponseCode;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.ResponseMessage;
import com.ggwp.memberservice.dto.response.auth.SignUpResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest

class AuthServiceTest {
    @Autowired
    private AuthService sut;
    private SignUpRequestDto duplicateEmailRequest; // 중복된 이메일 회원가입 요청
    private SignUpRequestDto duplicateLolNickTagRequest; // 중복된 롤 닉네임과 태그 회원가입 요청


    @Test
    @DisplayName(" 비밀번호 불일치")
    void createUserWithMismatchedPasswords() {
        // given
        SignUpRequestDto request = SignUpRequestDto.builder()
                .email("test@example.com")
                .password("Password1!")
                .lolNickname("TestNick")
                .tag("TestTag")
                .build();


        // when
        ResponseEntity<?> response = sut.signUp(request);
        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }


    @BeforeEach //BeforeEach는 각 테스트 메소드가 실행되기 전에 실행되는 메소드입니다.
    public void setup() {
        //@Builder를 사용하면 생성자를 사용하지 않고 객체를 생성할 수 있습니다.
        duplicateEmailRequest = SignUpRequestDto.builder()  // 중복된 이메일 회원가입 요청
                .email("existingUser@example.com")
                .password("anotherStrongPassword!")
                .lolNickname("엘리스바이")
                .tag("KR1")
                .agreedPersonal(true)
                .build();

        duplicateLolNickTagRequest = SignUpRequestDto.builder() // 중복된 롤 닉네임과 태그 회원가입 요청
                .email("uniqueUser@example.com")
                .password("uniquePassword123!")
                .lolNickname("엘리스바이") // Assuming this is a duplicate
                .tag("KR1") // Assuming this is a duplicate
                .agreedPersonal(true)
                .build();


    }

    @Test
    @DisplayName("회원가입 성공")
    public void testSignUp_Success() {
        // given:
        SignUpRequestDto validSignUpRequest = SignUpRequestDto.builder()
                .email("test19@exple.com")
                .password("@rl2Asd123")
                .lolNickname("엘리스바이링1")
                .tag("KR1")
                .agreedPersonal(true)
                .build();

        // when
        ResponseEntity<?> response = sut.signUp(validSignUpRequest);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
}