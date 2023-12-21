package com.ggwp.announceservice.entity;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.time.LocalDateTime;

// 공지사항 정보를 저장하는 엔티티.
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //공지사항 제목(최대 50자)
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String title;

    //공지사항 내용 (최대 1000자)
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String content;

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private Timestamp updatedAt = Timestamp.valueOf(LocalDateTime.now());

    //생성자를 담당하는 정적 메서드
    public static Announce create(String title, String content) {
        return new Announce()
                .setTitle(title)
                .setContent(content)
                .setCreatedAt(Timestamp.valueOf(LocalDateTime.now()))
                .setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));

    }

    //엔티티 수정을 위한 메서드
    public void updateAnnounce(RequestAnnounceDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();

        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }
}
