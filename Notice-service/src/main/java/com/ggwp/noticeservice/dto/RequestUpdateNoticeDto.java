package com.ggwp.noticeservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestUpdateNoticeDto {

    // 알림 PK
    @NotNull(message = "noticeID는 Null이 되서는 안됩니다.")
    private Long noticeId;

    // enum code 번호
    @NotNull(message = "code가 Null 이면 업데이트 할 수 없습니다.")
    private int code;
}
