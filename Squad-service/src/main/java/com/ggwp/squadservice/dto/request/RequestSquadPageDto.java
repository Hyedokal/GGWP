package com.ggwp.squadservice.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

public class RequestSquadPageDto {
    @Data
    @Accessors(chain = true)
    public static class Search {
        private boolean outdated = false;
        private int page = 0;
        private int size = 15;
    }
}
