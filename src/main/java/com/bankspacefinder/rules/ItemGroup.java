package com.bankspacefinder.rules;

import lombok.Getter;

class ItemGroup {
    @Getter
    private final String name;
    @Getter
    private final int[] itemIDs;

    public ItemGroup(String name, int... ids) {
        this.name = name;
        this.itemIDs = ids;
    }

    public boolean contains(int id) {
        for (int itemID : itemIDs) {
            if (itemID == id) {
                return true;
            }
        }
        return false;
    }
}