package com.ggwp.announceservice.repository;

import com.ggwp.announceservice.entity.Announce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AnnounceRepository extends JpaRepository<Announce, Long> {
    //공지사항 수정을 위한 메서드
    Optional<Announce> findByaId(Long aId);

}
