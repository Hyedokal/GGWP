package com.ggwp.memberservice.service;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.RequestCreateMemberDto;
import com.ggwp.memberservice.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class MemberService implements IMember {

    private static final String ALLOW_SPECIAL_CHAR = "!@#$%^&*()";

    private final MemberRepository memberRepository;

    private final IBadWord badWordService;


    /**
     * 회원생성
     * 1. id = 알파벳+숫자조합만 가능. 첫글자는 알파벳만 허용
     * 2. password = 알파벳+숫자+특수문자 조합만 가능, 최소 6자리 이상
     * 3. nickname = 한글+영어+숫자만 가능, 최소 4자리 이상, 금칙어 리스트의 단어를 포함하면 안됨
     * 4. 이미 가입된 userId인 경우, 이미 사용중인 userId라고 응답
     * @param requestCreateMemberDto
     * @return
     */
    @Override
    public Member.Vo createUser(RequestCreateMemberDto requestCreateMemberDto) {
        // 유저 id 생성 규칙 체크
        validateUserIdPolicy(requestCreateMemberDto.getUserId());

        // 패스워드 정책 체크
        validatePasswordPolicy(requestCreateMemberDto.getPassword());

        // 닉네임 금칙어 포함여부 체크
        var badWords = badWordService.extractBadWords(requestCreateMemberDto.getNickname());
        if (!badWords.isEmpty()) {
            var badWord = badWords.stream().collect(Collectors.joining(","));
            throw new RuntimeException("닉네임에 금칙어가 포함되어 있습니다. [" + badWord + "]");
        }

        memberRepository.findByUserId(requestCreateMemberDto.getUserId())
                .ifPresent(it -> {
                    throw new RuntimeException("이미 사용중인 아이디입니다.");
                });

        Member member = requestCreateMemberDto.toEntity();

        return memberRepository.save(member).toVo();
    }

    private void validateUserIdPolicy(String userId) {
        if (StringUtils.length(userId) < 6) {
            throw new RuntimeException("아이디는 최소 6자리 이상이어야 합니다.");
        }

        String regex = "^[a-zA-Z][a-zA-Z0-9]{5,}$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(userId);
        if (!matcher.matches()) {
            throw new RuntimeException("아이디는 영문자와 숫자 조합으로만 가능하고, 첫글자는 반드시 영문자로 시작해야 합니다.");
        }
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

    private void validateNicknamePolicy(String nickname) {
        if (StringUtils.length(nickname) < 4) {
            throw new RuntimeException("닉네임은 최소 4자리 이상이어야 합니다.");
        }

        String regex = "^[a-zA-Z0-9가-힣]{4,}$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(nickname);
        if (!matcher.matches()) {
            throw new RuntimeException("닉네임은 영문자, 숫자, 한글 조합으로만 가능합니다.");
        }

        String[] forbiddenWords = {"admin", "administrator", "운영자", "관리자"};
        Arrays.stream(forbiddenWords).forEach(it -> {
            if (nickname.contains(it)) {
                throw new RuntimeException("닉네임에 금칙어가 포함되어 있습니다.");
            }
        });
    }
}
