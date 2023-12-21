package com.ggwp.noticeservice.common.enums;

import lombok.Getter;


@Getter
public enum MessageEnum {

    APPLY(2,"매칭을 신청하였습니다."),
    ACCEPT(3,"매칭을 수락하였습니다."),
    REJECT(4,"매칭을 거절했습니다.");

    private final int code;
    private final String message;

    public static String getByCode(int code) {
        for (MessageEnum messageCode : values()) {
            if (messageCode.getCode() == code) {
                return messageCode.getMessage();
            }
        }
        throw new IllegalArgumentException("No matching constant for code: " + code);
    }

    MessageEnum(int code, String message){
        this.code = code;
        this.message = message;
    }
}
