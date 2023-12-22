package com.ggwp.noticeservice.common.enums;

import lombok.Getter;

@Getter
public enum NoticeEnum {

    UNREAD(1, "읽지 않음"),
    READ(2, "읽음"),  // 프론트에서 알람을 여는 순간 noticeId , 2 를 보냄
    ACCEPTED(3, "수락"), // 프론트에서 매칭자가 매칭을 누르면 noticeId , 3 을 보냄
    REJECTED(4, "거절");

    private final int code;
    private final String description;

    NoticeEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }


    public static NoticeEnum getByCode(int code) {
        switch (code) {
            case 1:
                return UNREAD;
            case 2:
                return READ;
            case 3:
                return ACCEPTED;
            case 4:
                return REJECTED;
            default:
                throw new IllegalArgumentException("No matching constant for code: " + code);
        }
    }


}
