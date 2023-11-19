package com.ggwp.searchservice.match.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PerksDto {

    @JsonProperty("styles")
    private List<PerkStyleDto> styles;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PerkStyleDto {
        @JsonProperty("description")
        private String description;

        @JsonProperty("style")
        private int style;
    }
}