package com.example.faceitspring.models;
import java.util.Map;

public enum Level {
    LVL1, LVL2, LVL3, LVL4, LVL5, LVL6, LVL7, LVL8, LVL9, LVL10;

    private static final Map<Level, Integer> LevelBaseElo = Map.of(
            LVL1, 0,
            LVL2, 300,
            LVL3, 500,
            LVL4, 700,
            LVL5, 925,
            LVL6, 1250,
            LVL7, 1500,
            LVL8, 1700,
            LVL9, 1875,
            LVL10, 2040
    );

    public int getBaseElo() {
        return LevelBaseElo.get(this);
    }

    public static Level getLevel(int elo) {
        Level result = LVL1;

        for (Level level : values()) {
            if (elo >= level.getBaseElo()) {
                result = level;
            } else {
                break;
            }
        }

        return result;
    }
}

