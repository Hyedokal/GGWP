package com.ggwp.memberservice.dto.response;

public interface ResponseMessage {
    
    String SUCCESS = "Success."; // 성공

    String VALIDATION_FAILED = "Validation failed.";
    String DUPLICATED_EMAIL = "Duplicate email.";

    String UNCHECK_PASSWORD ="비밀번호 불일치";

    String SIGN_IN_FAILED = "Login information mismatch.";

    String NO_PERMISSION = "Do not have permission.";

    String DATABASE_ERROR = "Database error.";

}
