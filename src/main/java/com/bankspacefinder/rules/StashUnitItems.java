package com.bankspacefinder.rules;

import net.runelite.client.game.ItemManager;
import static net.runelite.api.ItemID.*;
import net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit;
import static net.runelite.client.plugins.cluescrolls.clues.emote.STASHUnit.*;
import static com.bankspacefinder.rules.StashTier.*;

import java.util.ArrayList;
import java.util.List;

public enum StashUnitItems {
    BEGINNER1(GYPSY_TENT_ENTRANCE, BEGINNER, new int[]{GOLD_RING, GOLD_AMULET}),
    BEGINNER2(BOB_AXES_ENTRANCE, BEGINNER, new int[]{BRONZE_AXE, LEATHER_BOOTS}),
    BEGINNER3(FINE_CLOTHES_ENTRANCE, BEGINNER, new int[]{CHEFS_HAT, RED_CAPE})

    ;

    private final STASHUnit stashUnit;
    private final StashTier tier;
    private final int[] items;
    private final ItemGroup[] itemGroups;

    StashUnitItems(STASHUnit id, StashTier tier, int[] itemIDs, ItemGroup... itemGroups) {
        this.stashUnit = id;
        this.tier = tier;
        items = itemIDs;
        this.itemGroups = itemGroups;
    }

    public static List<Freeable> getFreeables() {
        List<Freeable> list = new ArrayList<>();
        for (StashUnitItems stash : values()) {
            List<Integer> itemIDs = stash.getAllRelevantItemIDs();
            for (int id : itemIDs) {
                list.add(new Freeable(id, stash::getMessage));
            }
        }
        return list;
    }

    public String getMessage(int id, ItemManager itemManager) {
        String result = itemManager.getItemComposition(id).getName();
        result += " can be stored in a " + tier.getName() + " STASH unit with ";

        for (int item : items) {
            if (item != id) {
                result += itemManager.getItemComposition(item).getName() + " ";
            }
        }
        for (ItemGroup g : itemGroups) {
            if (!g.contains(id)) {
                result += g.getName() + " ";
            }
        }
        //result += "word word word word word wordwordwordword word word word word word word word word word word word word word word word word";
        System.out.println("result:" + result);
        return result;
    }

    private List<Integer> getAllRelevantItemIDs() {
        List<Integer> ids = new ArrayList<>();
        for (int id : items) {
            ids.add(id);
        }
        for (ItemGroup group : itemGroups) {
            for (int id : group.getItemIDs()) {
                ids.add(id);
            }
        }
        return ids;
    }
}