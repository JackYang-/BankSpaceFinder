package com.bankspacefinder.rules;

import lombok.Getter;

@Getter
public enum StashTier {
    BEGINNER("Beginner"),
    EASY("Easy"),
    MEDIUM("Medium"),
    HARD("Hard"),
    ELITE("Elite"),
    MASTER("Master")
    ;

    public final String name;

    StashTier(String name) {
        this.name = name;
    }
}