package com.ggwp.memberservice.dto.response;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseCode {

    SUCCESS("SU"),
    VALIDATION_FAILED("VF"),
    DUPLICATED_EMAIL("DE"),
    UNCHECK_PASSWORD("UP"),
    DUPLICATED_LOL_NICKNAME_TAG("DLNT"),
    DUPLICATED_NICKNAME("DN"),
    DUPLICATED_TEL_NUMBER("DT"),
    NOT_EXIST_USER("NU"),
    NOT_EXIST_BOARD("NB"),
    SIGN_IN_FAILED("SF"),
    NO_PERMISSION("NP"),
    DATABASE_ERROR("DBE");

    private final String code;

    ResponseCode(String code) {
        this.code = code;
    }
    @JsonValue  // 이게 있어야 json으로 변환될 때 code로 변환됨
    public String getCode() {
        return code;
    }

}
