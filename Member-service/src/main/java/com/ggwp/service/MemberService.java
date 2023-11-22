package com.ggwp.service;


import com.ggwp.domain.Member;
import com.ggwp.dto.RequestCreateMemberDto;
import com.ggwp.global.exception.CustomException;
import com.ggwp.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import static com.ggwp.global.exception.ErrorCode.ALREADY_EXIST_EMAIL;
import static com.ggwp.global.exception.ErrorCode.ALREADY_EXIST_NICKNAME;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;


    // 회원가입
    public void createUser(RequestCreateMemberDto requestCreateMemberDto) {

        if(memberRepository.existsByEmail(requestCreateMemberDto.getEmail())){
            throw new CustomException(ALREADY_EXIST_EMAIL);
        }else if(memberRepository.existsBylolNickname(requestCreateMemberDto.getLolNickname())){
            throw  new CustomException(ALREADY_EXIST_NICKNAME);
        }

        Member member = requestCreateMemberDto.toEntity();
        memberRepository.save(member);
    }


}
