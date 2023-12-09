package com.ggwp.noticeservice.repository;


import com.ggwp.noticeservice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoticeRepository extends JpaRepository<Notice,Long> {

    Optional<List<Notice>> findNoticesByReceiverNameAndReceiverTag(String receiverName, String receiverTag);

    Optional<Notice> findNoticeById(Long id);

}
