package com.ggwp.announceservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

// 공지사항 정보를 저장하는 엔티티.
@Entity
@NoArgsConstructor @RequiredArgsConstructor
@AllArgsConstructor @Getter @ToString @Builder
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    //공지사항 제목
    private String aTitle;

    //공지사항 내용 (최대 1000자)
    private String aContent;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

}
