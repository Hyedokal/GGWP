package com.ggwp.searchservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GameMode {

    DRAFT_PICK_5V5(400, " DRAFT_PICK_5V5"),
    RANKED_SOLO_5x5(420, "RANKED_SOLO_5x5"),
    RANKED_FLEX_SR(440, "RANKED_FLEX_SR"),
    HOWLING_ABYSS(450, "HOWLING_ABYSS");

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
