package com.bankspacefinder.rules;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.runelite.client.game.ItemManager;

@RequiredArgsConstructor
public class Freeable {
    @Getter
    private final int itemID;

    private final Rule rule;

    private static ItemManager itemManager;

    @Getter
    private String message;

    public static void setItemManager(ItemManager manager) {
        itemManager = manager;
    }

    public boolean isFreeable() {
        message = rule.evaluate(itemID, itemManager);
        return !message.isEmpty();
    }

}
