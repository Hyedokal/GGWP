package com.ggwp.dto;

import com.ggwp.domain.Member;
import com.ggwp.global.entity.RoleType;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RequestCreateMemberDto {

    private String email;
    private String password;
    private String lolNickname;


    public Member toEntity() {
        // 암호화 비번을 저장하기 위해 필요한 암호화 라이브러리
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return Member.builder()
                .email(email)
                .password(bCryptPasswordEncoder.encode(password))
                .lolNickname(lolNickname)
                .role(RoleType.USER)
                .build();
    }

}
