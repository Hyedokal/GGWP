package com.ggwp.memberservice.service.impl;


import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.request.auth.SignInRequestDto;
import com.ggwp.memberservice.dto.request.auth.SignUpRequestDto;
import com.ggwp.memberservice.dto.response.ResponseDto;
import com.ggwp.memberservice.dto.response.auth.SignInResponseDto;
import com.ggwp.memberservice.dto.response.auth.SignUpResponseDto;
import com.ggwp.memberservice.provider.JwtProvider;
import com.ggwp.memberservice.repository.MemberRepository;
import com.ggwp.memberservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class AuthServiceImpl implements AuthService {
    private static final String ALLOW_SPECIAL_CHAR = "!@#$%^&*()";  //허용된 특수문자
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    /**
     * 회원생성
     * 1. email = 알파벳+숫자조합만 가능. 첫글자는 알파벳만 허용 , 이메일 형식이여야함.
     * 2. password = 알파벳+숫자+특수문자 조합만 가능, 최소 6자리 이상
     * 3. lol nickname = 한글+영어+숫자만 가능, 최소 3자리 이상, 금칙어 리스트의 단어를 포함하면 안됨
     * 4. tag = 10자리이하
     * 5. 이미 가입된 userId인 경우, 이미 사용중인 userId라고 응답
     * @param signUpRequestDto
     * @return
     */
    @Override
    public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto signUpRequestDto) {
        try {

            // 유저 id 생성 규칙 체크
            validateEmailPolicy(signUpRequestDto.getEmail());

            // 패스워드 정책 체크
            validatePasswordPolicy(signUpRequestDto.getPassword());



            String email = signUpRequestDto.getEmail();

            boolean hasEmail = memberRepository.existsByEmail(email);
            if (hasEmail) return SignUpResponseDto.duplicateEmail();

            Long count = memberRepository.countByLolNicknameAndTag(signUpRequestDto.getLolNickname(), signUpRequestDto.getTag() );
            if (count > 0) {
                return SignUpResponseDto.duplicateLolNickTag();
            }

            Member member =signUpRequestDto.toEntity();
            memberRepository.save(member);


        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignUpResponseDto.success();

    }


    private void validatePasswordPolicy(String password) {
        if (StringUtils.length(password) < 6) {
            throw new RuntimeException("비밀번호는 최소 6자리 이상이어야 합니다.");
        }

        AtomicBoolean useAlphabet = new AtomicBoolean(false);
        AtomicBoolean useNumber = new AtomicBoolean(false);
        AtomicBoolean useSpecialChar = new AtomicBoolean(false);

        Arrays.stream(password.split("")).forEach(it -> {
            if (it.matches("[a-zA-Z]")) {
                useAlphabet.set(true);
            } else if (it.matches("[0-9]")) {
                useNumber.set(true);
            } else if (ALLOW_SPECIAL_CHAR.contains(it)) {
                useSpecialChar.set(true);
            } else {
                throw new RuntimeException("비밀번호는 영문자, 숫자, 특수문자만 가능합니다.");
            }
        });
        if (!useAlphabet.get() || !useNumber.get() || !useSpecialChar.get()) {
            throw new RuntimeException("비밀번호는 영문자, 숫자, 특수문자를 모두 포함해야 합니다.");
        }
    }

    private void validateEmailPolicy(String email) {
        if (StringUtils.length(email) < 6) {
            throw new RuntimeException(" 이메일은  최소 6자리 이상이어야 합니다.");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()) {
            throw new RuntimeException("이메일 형식에 맞지 않습니다.");
        }

    }



    //로그인
    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto signInRequestDto) {
        String token = null;
        try {

            String email = signInRequestDto.getEmail();
            Member member = memberRepository.findByEmail(email);
            if (member == null) return SignInResponseDto.signInFailed();

            String password = signInRequestDto.getPassword();
            String encodedPassword = member.getPassword();

            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) return SignInResponseDto.signInFailed();

            token = jwtProvider.create(member.getUuid(), member.getLolNickname(), member.getTag(), member.getRole());

        } catch(Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(token);

    }


}
