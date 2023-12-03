package com.ggwp.memberservice.dto.response;

public interface ResponseCode {

    String SUCCESS = "SU";  //성공

    String VALIDATION_FAILED = "VF";
    String DUPLICATED_EMAIL = "DE";
    String UNCHECK_PASSWORD = "UP"; //패스워드 불일치


    String DUPLICATED_NICKNAME = "DN";
    String DUPLICATED_TEL_NUMBER = "DT";
    String NOT_EXIST_USER = "NU";
    String NOT_EXIST_BOARD = "NB";

    String SIGN_IN_FAILED = "SF";

    String NO_PERMISSION = "NP";

    String DATABASE_ERROR = "DBE";

}
