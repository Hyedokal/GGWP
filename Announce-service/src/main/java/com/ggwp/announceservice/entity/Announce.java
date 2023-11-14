package com.ggwp.announceservice.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

// 공지사항 정보를 저장하는 엔티티.
@Entity
@RequiredArgsConstructor
@AllArgsConstructor @Getter @ToString @Builder
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    //공지사항 제목
    private String aTitle;

    //공지사항 내용 (최대 1000자)
    @Column
    @Length(max = 1000)
    private String aContent;

    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime updatedAt = LocalDateTime.now();

    //엔티티 수정을 위한 메서드
    public void updateAnnounce(String aTitle, String aContent){
        this.aTitle = aTitle;
        this.aContent = aContent;
        this.updatedAt = LocalDateTime.now();
    }
}
