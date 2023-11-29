package com.ggwp.announceservice.entity;

import com.ggwp.announceservice.dto.request.RequestAnnounceDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

// 공지사항 정보를 저장하는 엔티티.
@Entity
@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Announce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aId;

    //공지사항 제목(최대 50자)
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String aTitle;

    //공지사항 내용 (최대 1000자)
    @Column(columnDefinition = "varchar(1000)", nullable = false)
    private String aContent;

    // mhlee: 이부분은 자료형도 Timestamp를 사용하는게 좋을것 같습니다.
    // 사실 자료형을 Timestamp를 사용하면 당연히 자료형도 Timestamp를 사용했는데, 혹시 이상없다면 두셔도 됩니다.
    // 테스트해보시고, 저도 결과가 어떤지 궁금하네요.
    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    //mhlee: 정적 생성자 잘 만들었습니다. 다만, 정적 생성자도 메소드이니, 소문자로 하는게 좋을 것 같습니다.
    //생성자를 담당하는 정적 메서드
    public static Announce create(String aTitle, String aContent) {
        return new Announce()
                .setATitle(aTitle)
                .setAContent(aContent)
                .setCreatedAt(LocalDateTime.now())
                .setUpdatedAt(LocalDateTime.now());
    }

    //엔티티 수정을 위한 메서드
    public void updateAnnounce(RequestAnnounceDto dto) {
        this.aTitle = dto.getATitle();
        this.aContent = dto.getAContent();
        this.updatedAt = LocalDateTime.now();
    }

}
