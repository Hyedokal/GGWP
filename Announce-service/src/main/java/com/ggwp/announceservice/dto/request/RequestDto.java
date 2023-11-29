package com.ggwp.announceservice.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

public class RequestDto {

    @Data
    @Accessors(chain = true)
    public static class Search {
        private String title;

        private int page = 0;

        private int size = 10;
    }
}
