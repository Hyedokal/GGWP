package com.ggwp.memberservice.dto.response;

public enum ResponseCode {

    SUCCESS("SU"),
    VALIDATION_FAILED("VF"),
    DUPLICATED_EMAIL("DE"),
    UNCHECK_PASSWORD("UP"),
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

    public String getCode() {
        return code;
    }

}
