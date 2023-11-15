package com.ggwp.noticeservice.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateNoticeDto {

    // 알림 PK
    private Long noticeId;

    // enum code 번호
    private int code;
}
