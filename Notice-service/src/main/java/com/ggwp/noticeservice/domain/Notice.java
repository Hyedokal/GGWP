package com.ggwp.noticeservice.domain;
import com.ggwp.noticeservice.enums.NoticeEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    // 알림의 상태
    @Enumerated(value = EnumType.STRING)
    private NoticeEnum status;

    // 보낸 사람
    private Long senderId;

    // 받는 사람
    private Long receiverId;

    @Builder
    public Notice(Long senderId, Long receiverId, NoticeEnum status){
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.status = status;
    }
    public void updateNotice(int code){
        this.status = NoticeEnum.getByCode(code);
    }
}
