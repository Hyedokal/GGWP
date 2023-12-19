package com.ggwp.searchservice.common.exception;

import com.ggwp.searchservice.common.dto.FrontDto;
import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    @Getter
    public static class NotFoundAccountException extends CustomException {
        private final transient FrontDto frontDto;

        public NotFoundAccountException(FrontDto frontDto, ErrorCode errorCode) {
            super(errorCode);
            this.frontDto = frontDto;
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class FeignClientError extends RuntimeException {
        private final FeignException feignException;
        private final ErrorCode errorCode;
    }
}
