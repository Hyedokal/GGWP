package com.example.noticeservice.enums;

import lombok.Getter;

@Getter
public enum NoticeEnum {

    UNREAD(1, "읽지 않음"),
    READ(2, "읽음"),
    ACCEPTED(3, "수락"),
    REJECTED(4, "거절");

    private final int code;
    private final String description;

    NoticeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
