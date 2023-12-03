package com.ggwp.memberservice.service;

import com.ggwp.memberservice.dto.request.auth.SignUpRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest

class AuthServiceTest {
    @Autowired
    private  AuthService sut;


    @Test
    @DisplayName(" 비밀번호 불일치")
    void createUserWithMismatchedPasswords() {
        // given
        SignUpRequestDto request = SignUpRequestDto.builder()
                .email("test@example.com")
                .password("Password1!")
                .userPasswordCheck("Password2!") // intentionally mismatched
                .lolNickname("TestNick")
                .tag("TestTag")
                .build();


        // when
        ResponseEntity<?> response = sut.signUp(request);
        // then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

    }

}