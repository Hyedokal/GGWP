package com.ggwp.searchservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameMode {

    DRAFT_PICK_5V5(400, "일반게임"),
    RANKED_SOLO(420, "솔로랭크"),
    RANKED_FLEX(440, "자유랭크"),
    HOWLING_ABYSS(450, "무작위 총력전");

    private final int queueId;
    private final String description;

    public static GameMode getByQueueId(int queueId) {
        for (GameMode gameMode : GameMode.values()) {
            if (gameMode.queueId == queueId) {
                return gameMode;
            }
        }
        throw new IllegalArgumentException("No matching constant for queueId: " + queueId);
    }

}
