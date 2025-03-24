package org.midnight.midnightFish.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.midnight.midnightFish.MidnightFish.LeaderstatsConfig;
import static org.midnight.midnightFish.MidnightFish.pl;

public class InitializeConfigValues {

    public static HashMap<String, Double> RarityChances = new HashMap<>();
    public static HashMap<String, Double> WeightChances = new HashMap<>();
    public static double GarbageChance;
    public static HashMap<String, HashMap<String, ArrayList<String>>> Biomes = new HashMap<>();
    public static HashMap<String, ArrayList<String>> Garbage = new HashMap<>();
    public static HashMap<String, String> Translates = new HashMap<>();
    public static HashMap<String, String> RarityColors = new HashMap<>();
    public static HashMap<String, String> FishRarities = new HashMap<>();

    public static void Init() {
        RarityChances.put("legendary", pl.getConfig().getDouble("chances.legendary"));
        RarityChances.put("epic", pl.getConfig().getDouble("chances.epic"));
        RarityChances.put("rare", pl.getConfig().getDouble("chances.rare"));

        WeightChances.put("big", pl.getConfig().getDouble("chances.big-weight"));
        WeightChances.put("medium", pl.getConfig().getDouble("chances.big-weight"));

        GarbageChance = pl.getConfig().getDouble("chances.garbage");

        for (String biome : pl.getConfig().getConfigurationSection("biomes").getKeys(false)) {
            HashMap<String, ArrayList<String>> Rarities = GetDefaultRaritiesHashMap();
            for (String rarity : pl.getConfig().getConfigurationSection("biomes." + biome).getKeys(false)) {
                ArrayList<String> CurrentRarityFish = new ArrayList<>(pl.getConfig().getConfigurationSection("biomes." + biome + "." + rarity).getKeys(false));
                for (String Fish : CurrentRarityFish) {
                    FishRarities.put(Fish, rarity);
                }
                Rarities.put(rarity, CurrentRarityFish);
            }
            Biomes.put(biome, Rarities);
        }

        Garbage = GetDefaultRaritiesHashMap();
        for (String rarity : pl.getConfig().getConfigurationSection("garbage").getKeys(false)) {
            ArrayList<String> CurrentRarityGarbage = new ArrayList<>(pl.getConfig().getConfigurationSection("garbage." + rarity).getKeys(false));
            Garbage.put(rarity, CurrentRarityGarbage);
        }

        for (String Item : pl.getConfig().getConfigurationSection("translates").getKeys(false)) {
            Translates.put(Item, pl.getConfig().getString("translates." + Item));
        }

        for (String Rarity : pl.getConfig().getConfigurationSection("colors").getKeys(false)) {
            RarityColors.put(Rarity, pl.getConfig().getString("colors." + Rarity));
        }

//        System.out.println(RarityColors);
//        System.out.println(Translates);
//        System.out.println(Biomes);
//        System.out.println(Garbage);
    }

    public static HashMap<String, ArrayList<String>> GetDefaultRaritiesHashMap() {
        HashMap<String, ArrayList<String>> rarities = new HashMap<>();
        rarities.put("legendary", new ArrayList<>());
        rarities.put("epic", new ArrayList<>());
        rarities.put("rare", new ArrayList<>());
        rarities.put("common", new ArrayList<>());

        return rarities;
    }
}
