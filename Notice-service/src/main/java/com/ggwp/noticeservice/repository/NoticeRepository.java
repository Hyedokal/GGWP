package com.ggwp.noticeservice.repository;


import com.ggwp.noticeservice.domain.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice,Long> {
    Notice findNoticeById(Long id);


    List<Notice> findAll();

}
