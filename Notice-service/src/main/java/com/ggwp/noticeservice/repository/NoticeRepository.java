package com.ggwp.noticeservice.repository;


import com.ggwp.noticeservice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    Optional<Notice> findNoticeById(Long id); // 알림의 아이디로 알림 찾기

    List<Notice> findNoticeByReceiverId(Long receiverId); // 받는 사람으로 알림 찾기(리스트)
    List<Notice> findNoticeBySenderId(Long senderId); // 보낸 사람으로 알림 찾기(리스트)
    List<Notice> findAll(); // 알림 전체 리스트

}
