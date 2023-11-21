package com.ggwp.memberservice.controller.login.service;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private  final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member =  memberRepository.findByUserId(userId)
                .orElseThrow(()->new UsernameNotFoundException("해당 이메일이 존재 하지않습니다."));
        return org.springframework.security.core.userdetails.User.builder()
                .username(member.getUserId())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();
    }



}
