package com.ggwp.global.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionAdvice {

    // CustomException 이 발생했을 때 처리하는 핸들러 메서드
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomException.CustomExceptionResponse> exceptionHandler(
            CustomException e) {
        log.error("'{}':'{}'", e.getErrorCode(), e.getErrorCode().getDetail());
        return ResponseEntity
                .status(e.getStatus())
                .body(CustomException.CustomExceptionResponse.builder()
                        .message(e.getMessage())
                        .code(e.getErrorCode().name())
                        .status(e.getStatus()).build());
    }

}