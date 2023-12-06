package com.ggwp.memberservice.dto.response;

public interface ResponseMessage {
    
    String SUCCESS = "Success.1"; // 성공

    String VALIDATION_FAILED = "Validation failed.";

    String UNCHECK_PASSWORD ="비밀번호 불일치";

    String SIGN_IN_FAILED = "Login information mismatch.";

    String NO_PERMISSION = "Do not have permission.";
    String NOT_EXIST_USER = "This user does not exist.";

    String DATABASE_ERROR = "Database error.";

    String DUPLICATED_EMAIL = "유저가 중복됩니다." ;
}
