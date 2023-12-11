package com.ggwp.noticeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SampleDto {

    private String receiverId;

    private String senderid;

    private String message;
}
