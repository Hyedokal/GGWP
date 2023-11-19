package com.ggwp.searchservice.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameMode {

    DRAFT_PICK_5V5(400, "일반게임"),
    RANKED_SOLO(420, "솔로랭크"),
    RANKED_FLEX(440, "자유랭크"),
    SNOW_ARURF(1010, "무작위 총력전");

    private final int queueId;
    private final String description;

}
