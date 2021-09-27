package com.bankspacefinder.rules;

import net.runelite.client.plugins.cluescrolls.clues.EmoteClue;

import javax.inject.Singleton;
import java.util.*;

public class FreeableManager {
    private static Map<Integer, List<Freeable>> ruleMap;

    public static void prepareRuleMap() {
        ruleMap = new HashMap<>();

        for (Freeable f : StashUnitItems.getFreeables()) {
            if (ruleMap.containsKey(f.getItemID())) {
                ruleMap.get(f.getItemID()).add(f);
            } else {
                ruleMap.put(f.getItemID(), new ArrayList<Freeable>() {
                    {
                        add(f);
                    }
                });

            }
        }
    }

    public static boolean contains(int id) {
        return ruleMap.containsKey(id);
    }

    public static List<Freeable> getFreeables(int id) {
        return ruleMap.get(id);
    }
}
