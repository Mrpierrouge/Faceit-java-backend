package com.example.faceitspring.models;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public enum Rank {
    BRONZE, SILVER, GOLD, PLATINIUM, EMERALD, DIAMOND, MASTER, GRANDMASTER, CHAMPION;

    private static final Map<Rank, Level> RankToLevelMapping = Map.of(
            BRONZE, Level.LVL2,
            SILVER, Level.LVL3,
            GOLD, Level.LVL4,
            PLATINIUM, Level.LVL5,
            EMERALD, Level.LVL6,
            DIAMOND, Level.LVL7,
            MASTER, Level.LVL8,
            GRANDMASTER, Level.LVL9,
            CHAMPION, Level.LVL10
    );
    private static final Map<String, Rank> RankFromString = Map.of(
            "BRONZE", BRONZE,
            "SILVER", SILVER,
            "GOLD", GOLD,
            "PLATINIUM", PLATINIUM,
            "EMERALD", EMERALD,
            "DIAMOND", DIAMOND,
            "MASTER", MASTER,
            "GRANDMASTER", GRANDMASTER,
            "CHAMPION", CHAMPION
    );

    public static Rank fromString(String value) {
        return RankFromString.getOrDefault(
                value.toUpperCase(),
                BRONZE
        );
    }

    public Level getLevel() {
        return RankToLevelMapping.get(this);
    }
    @JsonValue
    public Rank fromJson(String value){
        return fromString(value);
    }

}
