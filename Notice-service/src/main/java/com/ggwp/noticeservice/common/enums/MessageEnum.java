package com.ggwp.noticeservice.common.enums;

import lombok.Getter;


@Getter
public enum MessageEnum {

    APPLY("매칭을 신청하였습니다."),
    ACCEPT("매칭을 수락하였습니다."),
    REJECT("매칭을 거절했습니다.");

    private final String message;

    public static MessageEnum getByCode(int code) {
        switch (code) {
            case 2:
                return APPLY;
            case 3:
                return ACCEPT;
            case 4:
                return REJECT;
            default:
                throw new IllegalArgumentException("No matching constant for code: " + code);
        }
    }

    MessageEnum(String message){
        this.message = message;
    }
}
