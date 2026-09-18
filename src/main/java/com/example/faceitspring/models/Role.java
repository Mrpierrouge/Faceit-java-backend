package com.example.faceitspring.models;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Map;

public enum Role {
    TANK, DPS, SUPPORT;

    private static final Map<String, Role> RoleFromString = Map.of(
            "TANK", TANK,
            "DPS", DPS,
            "SUPPORT", SUPPORT
    );

    public static Role fromString(String value) {
        return RoleFromString.getOrDefault(
                value.toUpperCase(),
                DPS
        );
    }
    @JsonValue
    public Role fromJson(String value){
        return RoleFromString.get(value);
    }
}
