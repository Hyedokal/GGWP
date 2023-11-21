package com.ggwp.noticeservice.dto;

import com.ggwp.noticeservice.domain.Member;
import com.ggwp.noticeservice.domain.Notice;
import com.ggwp.noticeservice.enums.NoticeEnum;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestCreateNoticeDto {

    // 보낸 사람
    private Long senderId;

    // 받는 사람
    private Long receiverId;

    //알림의 상태
    private NoticeEnum status;
    public Notice toEntity(Long senderId, Long receiverId){
        return Notice.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .status(NoticeEnum.UNREAD)
                .build();
    }
}
