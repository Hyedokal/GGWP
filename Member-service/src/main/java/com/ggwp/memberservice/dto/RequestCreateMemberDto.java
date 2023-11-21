package com.ggwp.memberservice.dto;

import com.ggwp.memberservice.domain.Member;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder @ToString
public class RequestCreateMemberDto {

    private String userId;
    private String password;
    private String nickname;


    public Member toEntity() {
        // 암호화 비번을 저장하기 위해 필요한 암호화 라이브러리
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        return Member.builder()
                .userId(userId)
                .password(bCryptPasswordEncoder.encode(password))
                .nickname(nickname)
                .build();
    }

}
