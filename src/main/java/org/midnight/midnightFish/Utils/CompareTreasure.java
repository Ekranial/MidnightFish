package org.midnight.midnightFish.Utils;

import it.unimi.dsi.fastutil.Pair;
import org.midnight.midnightFish.Treasures.Treasure;

import java.util.Comparator;

public class CompareTreasure implements Comparator<Treasure> {
    @Override
    public int compare(Treasure o1, Treasure o2) {
        return Double.compare(o1.DropChance, o2.DropChance);
    }
}
