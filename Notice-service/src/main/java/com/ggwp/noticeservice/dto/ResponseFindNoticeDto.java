package com.ggwp.noticeservice.dto;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.enums.NoticeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFindNoticeDto {

    // notice_id
    private Long id;

    // 알림의 상태
    private NoticeEnum status;

    // 보낸 사람
    private Long senderId;

    // 받는 사람
    private Long receiverId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    public ResponseFindNoticeDto(Notice notice){
        this.id = notice.getId();
        this.status = notice.getStatus();
        this.senderId = notice.getSenderId();
        this.receiverId = notice.getReceiverId();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }

}
