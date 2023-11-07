package com.example.noticeservice.dto;

import com.example.noticeservice.domain.Notice;
import com.example.noticeservice.enums.NoticeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseFindNoticeDto {
    private Long id;

    // 내용
    private String content;

    // 알림 동의 여부
    private boolean checked;

    // 알림의 상태
    private NoticeEnum status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
    public ResponseFindNoticeDto(Notice notice){
        this.id = notice.getId();
        this.content = notice.getContent();
        this.checked = notice.isChecked();
        this.status = notice.getStatus();
        this.createdAt = notice.getCreatedAt();
        this.updatedAt = notice.getUpdatedAt();
    }

}
