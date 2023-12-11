package com.ggwp.noticeservice.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    NotFindNotice(HttpStatus.NOT_FOUND, "Notice를 DB에서 찾을 수 없습니다."),
    InValidException(HttpStatus.BAD_REQUEST, "Valid 값이 잘못되었습니다."),

    NotFeginException(HttpStatus.BAD_REQUEST, "Fegin을 하지 못했습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
