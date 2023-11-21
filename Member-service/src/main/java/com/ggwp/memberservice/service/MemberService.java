package com.ggwp.memberservice.service;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.RequestCreateMemberDto;
import com.ggwp.memberservice.global.exception.CustomException;
import com.ggwp.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ggwp.memberservice.global.exception.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.ggwp.memberservice.global.exception.ErrorCode.ALREADY_EXIST_NICKNAME;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;


    // 회원가입
    public void createUser(RequestCreateMemberDto requestCreateMemberDto) {

        if(memberRepository.existsByEmail(requestCreateMemberDto.getEmail())){
            throw new CustomException(ALREADY_EXIST_EMAIL);
        }else if(memberRepository.existsByNickname(requestCreateMemberDto.getNickname())){
            throw  new CustomException(ALREADY_EXIST_NICKNAME);
        }

        Member member = requestCreateMemberDto.toEntity();
        memberRepository.save(member);
    }


}
