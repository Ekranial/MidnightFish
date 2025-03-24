package org.midnight.midnightFish.Utils;

import it.unimi.dsi.fastutil.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.midnight.midnightFish.Utils.InitializeConfigValues.FishRarities;

public class Compare implements Comparator<Pair<String, Pair<String, Double>>> {

    @Override
    public int compare(Pair<String, Pair<String, Double>> o1, Pair<String, Pair<String, Double>> o2) {

        ArrayList<String> RarityOrder = new ArrayList<>(List.of("legendary", "epic", "rare", "common"));

        String RarityFish1 = FishRarities.getOrDefault(o1.left(), "common");
        String RarityFish2 = FishRarities.getOrDefault(o2.left(), "common");

//        System.out.println(RarityOrder.indexOf(RarityFish1) + " " + RarityOrder.indexOf(RarityFish2));
        if (RarityOrder.indexOf(RarityFish1) < RarityOrder.indexOf(RarityFish2)) return -1;
        if (RarityOrder.indexOf(RarityFish1) > RarityOrder.indexOf(RarityFish2)) return 1;

        if (o1.right().right() - o2.right().right() > 0) return -1;
        else if (o1.right().right() - o2.right().right() == 0) return 0;
        else if (o1.right().right() - o2.right().right() < 0) return 1;

        return 1;
    }
}
