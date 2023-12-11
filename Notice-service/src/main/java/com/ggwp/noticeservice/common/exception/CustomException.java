package com.ggwp.noticeservice.common.exception;

import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    @Getter
    @RequiredArgsConstructor
    public static class FeignClientError extends RuntimeException {
        private final FeignException feignException;
        private final ErrorCode errorCode;
    }
}
