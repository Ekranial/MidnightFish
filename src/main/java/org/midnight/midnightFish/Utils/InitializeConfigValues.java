package org.midnight.midnightFish.Utils;

import org.bukkit.configuration.ConfigurationSection;
import org.midnight.midnightFish.Garbage.Garbage;
import org.midnight.midnightFish.Treasures.Treasure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.midnight.midnightFish.MidnightFish.pl;

public class InitializeConfigValues {

    public static String RpUrl;
    public static ArrayList<String> RarityList = new ArrayList<>(List.of("legendary", "epic", "rare", "common"));
    public static int BaseLevel;
    public static int MaxLevel;
    public static ArrayList<Treasure> Treasures = new ArrayList<>();
    public static HashMap<Integer, Integer> RequiredExp = new HashMap<>();
    public static HashMap<String, Double> RarityExp = new HashMap<>();
    public static HashMap<String, Integer> RarityLvlReq = new HashMap<>();
    public static HashMap<String, Double> RarityChances = new HashMap<>();
    public static HashMap<String, Double> WeightChances = new HashMap<>();
    public static double GarbageChance;
    public static HashMap<String, HashMap<String, ArrayList<String>>> Biomes = new HashMap<>();
    public static HashMap<String, ArrayList<org.midnight.midnightFish.Garbage.Garbage>> Garbage = new HashMap<>();
    public static HashMap<String, String> Translates = new HashMap<>();
    public static HashMap<String, String> RarityColors = new HashMap<>();
    public static HashMap<String, String> FishRarities = new HashMap<>();

    public static void Init() {

        ClearData();

        RpUrl = pl.getConfig().getString("rp");

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

        Garbage = GetDefaultRaritiesGarbageHashMap();
        for (String rarity : RarityList) {
            ArrayList<Garbage> CurrentRarityGarbage = new ArrayList<>();
            if (!pl.getConfig().contains("garbage." + rarity)) continue;
            for (String name : pl.getConfig().getConfigurationSection("garbage." + rarity).getKeys(false)) {
                org.midnight.midnightFish.Garbage.Garbage CurGarbage = new Garbage(name, rarity, null);
                if (pl.getConfig().contains("garbage." + rarity + "." + name + ".model")) {
                    CurGarbage.CustomModelData = pl.getConfig().getString("garbage." + rarity + "." + name + ".model");
                }
                CurrentRarityGarbage.add(CurGarbage);
            }
            Garbage.put(rarity, CurrentRarityGarbage);
        }

        for (String Item : pl.getConfig().getConfigurationSection("translates").getKeys(false)) {
            Translates.put(Item, pl.getConfig().getString("translates." + Item));
        }

        for (String Rarity : pl.getConfig().getConfigurationSection("colors").getKeys(false)) {
            RarityColors.put(Rarity, pl.getConfig().getString("colors." + Rarity));
        }

        for (String rarity : pl.getConfig().getConfigurationSection("exp").getKeys(false)) {
            RarityExp.put(rarity, pl.getConfig().getDouble("exp." + rarity));
        }

        BaseLevel = pl.getConfig().getInt("levels.base");
        MaxLevel = pl.getConfig().getInt("levels.max");

        for (int lvl = BaseLevel + 1; lvl <= MaxLevel; ++lvl) {
            RequiredExp.put(lvl, pl.getConfig().getInt("levels." + lvl));
        }

        for (String rarity : pl.getConfig().getConfigurationSection("lvl_req").getKeys(false)) {
            RarityLvlReq.put(rarity, pl.getConfig().getInt("lvl_req." + rarity));
        }

        for (String name : pl.getConfig().getConfigurationSection("treasures").getKeys(false)) {
            ConfigurationSection CurTreasure = pl.getConfig().getConfigurationSection("treasures." + name);
            Treasure treasure = new Treasure(name, CurTreasure.getString("rarity"), CurTreasure.getDouble("chance"),
                    CurTreasure.getInt("lvl"), null);
            if (CurTreasure.contains("model")) {
                treasure.CustomModelData = CurTreasure.getString("model");
            }
            Treasures.add(treasure);
        }
        Treasures.sort(new CompareTreasure());

//        System.out.println(Treasures.get(0).CustomModelData);
//        System.out.println(RarityExp.get("legendary"));
//        System.out.println(RequiredExp);
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

    public static HashMap<String, ArrayList<Garbage>> GetDefaultRaritiesGarbageHashMap() {
        HashMap<String, ArrayList<Garbage>> rarities = new HashMap<>();
        rarities.put("legendary", new ArrayList<>());
        rarities.put("epic", new ArrayList<>());
        rarities.put("rare", new ArrayList<>());
        rarities.put("common", new ArrayList<>());

        return rarities;
    }

    public static void ClearData() {
        Treasures.clear();
        RequiredExp.clear();
        RarityExp.clear();
        RarityLvlReq.clear();
        RarityChances.clear();
        WeightChances.clear();
        Biomes.clear();
        Garbage.clear();
        Translates.clear();
        RarityColors.clear();
        FishRarities.clear();
    }
}
