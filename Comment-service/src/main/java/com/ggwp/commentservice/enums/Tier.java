package com.ggwp.commentservice.enums;

public enum Tier {
    IRON("IRON", "I"),
    BRONZE("BRONZE", "B"),
    SILVER("SILVER", "S"),
    GOLD("GOLD", "G"),
    PLATINUM("PLATINUM", "P"),
    EMERALD("EMERALD", "E"),
    DIAMOND("DIAMOND", "D"),
    MASTER("MASTER", "M"),
    GRANDMASTER("GRANDMASTER", "GM"),
    CHALLENGER("CHALLENGER", "C");

    private final String fullName;
    private final String abbreviation;

    Tier(String fullName, String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public static String getAbbreviationByFullName(String fullName) {
        for (Tier tier : values()) {
            if (tier.fullName.equals(fullName)) {
                return tier.abbreviation;
            }
        }
        return "";
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}

