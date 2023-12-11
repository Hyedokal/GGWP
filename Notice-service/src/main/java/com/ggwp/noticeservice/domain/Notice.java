package com.ggwp.noticeservice.domain;

import com.ggwp.noticeservice.common.enums.MessageEnum;
import com.ggwp.noticeservice.common.enums.NoticeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@DynamicInsert
@DynamicUpdate
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id",unique = true)
    private Long id;

    // 알림의 상태
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private NoticeEnum status;

    // 보낸 사람
    @Column(length = 63, nullable = false)
    private String senderName;
    @Column(nullable = false)
    private String senderTag;

    // 받는 사람
    @Column(length = 63, nullable = false)
    private String receiverName;
    @Column(nullable = false)
    private String receiverTag;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private MessageEnum msg;

    public void update(int code) {
        this.status = NoticeEnum.getByCode(code);
        this.msg = MessageEnum.getByCode(code);
    }

    @Version
    private int version;

}
