package com.bankspacefinder.rules;

import net.runelite.client.game.ItemManager;

public interface Rule {
    String evaluate(int id, ItemManager itemManager);
}
