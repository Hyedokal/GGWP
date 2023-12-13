package com.ggwp.squadservice.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

//댓글 feign을 위한 페이징 DTO.
public class RequestCommentPageDto {
    public static RequestCommentPageDto.Search create(Long sId, int page, int size) {
        return new RequestCommentPageDto.Search()
                .setSId(sId)
                .setPage(page)
                .setSize(size);
    }
    @Data
    @Accessors(chain = true)
    public static class Search {
        private Long sId;
        private int page = 0;
        private int size = 5;
    }
}
