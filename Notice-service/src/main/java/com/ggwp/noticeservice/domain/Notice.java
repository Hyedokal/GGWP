package com.ggwp.noticeservice.domain;
import com.ggwp.noticeservice.enums.NoticeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "notice_id")
    private Long id;

    // 내용
    private String content;

    // 알림 동의 여부
    private boolean checked;

    // 알림의 상태
    @Enumerated(value = EnumType.STRING)
    private NoticeEnum status;

}
