package com.ggwp.memberservice.provider;

import com.ggwp.memberservice.domain.UserRole;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JwtProviderTest {

    @Autowired
    JwtProvider jwtProvider;

    @Test
    @DisplayName("토큰 생성 및 검증")
    void createAndValid() {
        String jwt = jwtProvider.create("test@test", "testLolNickName", "testTag", UserRole.valueOf("ROLE_USER"));
        assertNotNull(jwt); // 토큰이 null이 아닌지 확인
        assertTrue(jwt.length() > 0); // 토큰의 길이가 0보다 큰지 확인

        //토큰을 검증하여 email 을 가져온다
        String email = jwtProvider.validate(jwt);
        assertNotNull(email); // email이 null이 아닌지 확인
        assertEquals("test@test", email); // email이 test@test인지 확인

        //3개의 단위로 구성되어있는지 확인
        String[] splitJwt = jwt.split("\\.");
        assertEquals(3, splitJwt.length);


    }

}