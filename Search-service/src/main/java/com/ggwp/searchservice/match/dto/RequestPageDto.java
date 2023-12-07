package com.ggwp.searchservice.match.dto;

import lombok.Data;
import lombok.experimental.Accessors;

//페이징 처리를 위한 DTO 클래스.
public class RequestPageDto {
    @Data
    @Accessors(chain = true)
    public static class Search {
        private int page = 0;
        private int size = 5;
    }
}
