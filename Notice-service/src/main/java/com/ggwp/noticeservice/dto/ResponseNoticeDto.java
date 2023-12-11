package com.ggwp.noticeservice.dto;
import com.ggwp.noticeservice.common.enums.MessageEnum;
import com.ggwp.noticeservice.common.enums.NoticeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseNoticeDto {

    // notice_id
    private Long id;

    private NoticeEnum status;

    // 보낸 사람
    private String senderName;

    private String senderTag;

    // 받는 사람
    private String receiverName;

    private String receiverTag;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private MessageEnum msg;

}
