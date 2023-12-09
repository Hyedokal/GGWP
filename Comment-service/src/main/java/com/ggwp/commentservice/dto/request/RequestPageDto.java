package com.ggwp.commentservice.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

//페이징 처리를 위한 DTO 클래스.
public class RequestPageDto {
    @Data
    @Accessors(chain = true)
    public static class Search {
        private Long sId;
        private int page = 0;
        private int size = 5;
    }
}
