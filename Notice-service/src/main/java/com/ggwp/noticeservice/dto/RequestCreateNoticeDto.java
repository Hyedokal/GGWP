package com.ggwp.noticeservice.dto;

import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.enums.NoticeEnum;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateNoticeDto {

    // 내용
    private String content;

    // 알림 동의 여부
    private boolean checked;

    // 알림의 상태
    private NoticeEnum status;
    public Notice toEntity(){
        return Notice.builder()
                .content("알림 내용입니다.")
                .checked(false)
                .status(NoticeEnum.UNREAD)
                .build();
    }
}
