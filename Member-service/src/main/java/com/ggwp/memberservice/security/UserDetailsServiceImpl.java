package com.ggwp.memberservice.security;

import com.ggwp.memberservice.domain.Member;
import com.ggwp.memberservice.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자 입니다."));
        return User.builder()
                .username(member.getUserId())
                .password(member.getPassword()) // Assuming getPassword returns the encoded password directly
                .roles(member.getRole().name()).build();
    }
}
