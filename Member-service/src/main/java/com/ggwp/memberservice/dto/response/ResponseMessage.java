package com.ggwp.memberservice.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseMessage {
    SUCCESS("Success.1"),
    VALIDATION_FAILED("Validation failed."),
    UNCHECK_PASSWORD("비밀번호 불일치"),
    SIGN_IN_FAILED("Login information mismatch."),
    NO_PERMISSION("Do not have permission."),
    NOT_EXIST_USER("This user does not exist."),
    DATABASE_ERROR("Database error."),
    DUPLICATED_EMAIL("유저가 중복됩니다.");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
    @JsonValue  // 이게 있어야 json으로 변환될 때 code로 변환됨
    public String getMessage() {
        return message;
    }
}