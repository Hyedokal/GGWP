package com.ggwp.memberservice.service;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.dto.RequestCreateMemberDto;
import com.ggwp.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;

    // 회원가입
    public void createUser(RequestCreateMemberDto requestCreateMemberDto) throws Exception {
        Member member = requestCreateMemberDto.toEntity();
        memberRepository.save(member);
    }




}
