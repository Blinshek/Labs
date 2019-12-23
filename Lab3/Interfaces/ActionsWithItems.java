package com.company.Interfaces;

import com.company.AbstractClasses.*;

public interface ActionsWithItems {
    default void give(Item item, MyCharacter to) {
        give(item, to, 1);
    }

    String give(Item item, MyCharacter to, int count);

    default String take(ChestLike chest, Item takenItem) {
        return take(chest, takenItem, 1);
    }

    default String take(ChestLike chest, Item takenItem, int count) {
        int itemInChestCount = chest.getItemCount(takenItem);
        int availableItemsCount = Math.min(itemInChestCount, count);

        if(availableItemsCount == 0)
            return String.format("в %s нет %s", chest, takenItem);
        else {
            chest.removeItem(takenItem, availableItemsCount);
            this.take(takenItem, availableItemsCount);
            return String.format("%s взял %s x%s из %s", this, takenItem, count, chest);
        }
    }

    default String take(Item takenItem) {
        return take(takenItem, 1);
    }

    String take(Item takenItem, int count);
}