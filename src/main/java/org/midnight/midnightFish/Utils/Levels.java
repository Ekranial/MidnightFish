package org.midnight.midnightFish.Utils;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

import static org.midnight.midnightFish.MidnightFish.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.Translates;

public class Levels {

    public static Pair<Integer, Double> GetPlrLvl(String Player) {
        ConfigurationSection CurrentPlrSection;
        if (!LevelsConfig.getKeys(false).contains(Player)) {
            CurrentPlrSection = LevelsConfig.createSection(Player);

            CurrentPlrSection.set("lvl", BaseLevel);
            CurrentPlrSection.set("exp", 0);
        } else {
            CurrentPlrSection = LevelsConfig.getConfigurationSection(Player);
        }

        return Pair.of(CurrentPlrSection.getInt("lvl"), CurrentPlrSection.getDouble("exp"));
    }

    public static void CreateLvlsConfig() {
        LevelsConfigFile = new File(pl.getDataFolder(), "levels.yml");
        if (!LevelsConfigFile.exists()) {
            LevelsConfigFile.getParentFile().mkdirs();
            pl.saveResource("levels.yml", false);
        }

        LevelsConfig = new YamlConfiguration();
        try {
            LevelsConfig.load(LevelsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    /* User Edit:
        Instead of the above Try/Catch, you can also use
        YamlConfiguration.loadConfiguration(customConfigFile)
    */
    }

    public static void UpdateLevel(Player player, String fish) {
        ConfigurationSection CurrentPlrSection;
        if (!LevelsConfig.getKeys(false).contains(player.getName())) {
            CurrentPlrSection = LevelsConfig.createSection(player.getName());

            CurrentPlrSection.set("lvl", BaseLevel);
            CurrentPlrSection.set("exp", 0);
        } else {
            CurrentPlrSection = LevelsConfig.getConfigurationSection(player.getName());
        }

        Pair<Integer, Double> NewExpLvl = GetNewLvl(CurrentPlrSection.getInt("lvl"), CurrentPlrSection.getDouble("exp"),
                RarityExp.get(FishRarities.get(fish)));

        if (NewExpLvl.first() > CurrentPlrSection.getInt("lvl")) {
            String prefix = "&7[&aMFish&7] &f";
            String out = prefix + "&aНовый уровень рыбалки &7[&6&l" + NewExpLvl.first() + "&f&7]";
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', out));
        }

        CurrentPlrSection.set("lvl", NewExpLvl.first());
        CurrentPlrSection.set("exp", NewExpLvl.second());

        try {
            LevelsConfig.save(LevelsConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Pair<Integer, Double> GetNewLvl(Integer CurLvl, Double CurExp, Double AddedExp) {
        double NewExp = CurExp + AddedExp;
        int NewLvl = CurLvl;

        for (int lvl = CurLvl + 1; lvl <= MaxLevel; ++lvl) {
            if (NewExp >= RequiredExp.get(lvl)) {
                NewExp -= RequiredExp.get(lvl);
                NewLvl = lvl;
            }
        }

        if (NewLvl == MaxLevel && NewExp > 0) {
            NewExp = 0;
        }

        return Pair.of(NewLvl, NewExp);
    }
}
