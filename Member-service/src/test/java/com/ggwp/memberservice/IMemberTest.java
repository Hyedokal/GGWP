package com.ggwp.memberservice;

import com.ggwp.memberservice.dto.RequestCreateMemberDto;
import com.ggwp.memberservice.service.IMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class IMemberTest {

    @Autowired
    private IMember sut;

    @Test
    @DisplayName("UserId의 첫글자는 반드시 알파벳 이어야 한다.")
    void firstCharacterOfUserIdShouldBeStartedAlphabet(){
        //given
        String userId = "1userId";
        var dto = RequestCreateMemberDto.builder()
                .userId(userId)
                .password("test1234!")
                .nickname("hello")
                .build();

        //when
        var exception = assertThrows(RuntimeException.class, () -> {
            sut.createUser(dto);
        });

        //then
        assertEquals("아이디는 영문자와 숫자 조합으로만 가능하고,첫글자는 반드시 영문자로 시작해야 합니다",exception.getMessage());
    }
}
