package com.ggwp.searchservice.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RuneEnums {
    DOMINATION(8100, "Domination", "7200_Domination.png", "지배"),
    INSPIRATION(8300, "Inspiration", "7203_Whimsy.png", "영감"),
    PRECISION(8000, "Precision", "7201_Precision.png", "정밀"),
    RESOLVE(8400, "Resolve", "7204_Resolve.png", "결의"),
    SORCERY(8200, "Sorcery", "7202_Sorcery.png", "마법");

    private final int id;
    private final String key;
    private final String icon;
    private final String name;

    public static RuneEnums getById(int id) {
        for (RuneEnums rune : RuneEnums.values()) {
            if (rune.getId() == id) {
                return rune;
            }
        }
        throw new IllegalArgumentException("No matching constant for id: " + id);
    }

}
