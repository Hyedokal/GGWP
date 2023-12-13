package com.ggwp.memberservice.repository;

import com.ggwp.memberservice.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);
    Member findByUuid (String uuid);


    boolean existsByEmail(String email); // 이메일 중복검사
}

