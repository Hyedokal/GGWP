package com.ggwp.repository;

import com.ggwp.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

     Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);



    //이메일 벨리
    boolean existsByEmail(String email);

    //닉네임체크
    boolean existsBylolNickname(String lolNickname);

}
