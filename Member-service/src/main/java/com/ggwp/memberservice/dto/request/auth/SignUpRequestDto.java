package com.ggwp.memberservice.dto.request.auth;

import com.ggwp.memberservice.domain.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SignUpRequestDto {

    @NotEmpty
    @Email(message = "잘못된 이메일 형식입니다")
    private String email;
    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{6,}$", message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 하며, 6자 이상이어야 합니다.")
    private String password;
    @NotEmpty
    private String userPasswordCheck;

    @Size(min = 3, max = 16, message = "닉네임은 3~16자 사이여야 합니다.")
    @NotEmpty
    private String lolNickname;

    @Size(max = 10, message = "태그는 10자리 미만이여야합니다.")
    @NotEmpty
    private String tag;


    public Member toEntity() {
        // 암호화 비번을 저장하기 위해 필요한 암호화 라이브러리
         PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password)) // todo: 시큐리티 적용하면 패스워드 인코더 적용
                .lolNickname(lolNickname)
                .tag(tag)
                .build();
    }
}
