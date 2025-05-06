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
import java.util.ArrayList;

import static org.midnight.midnightFish.MidnightFish.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.Translates;

public class Leaderstats {

    public static void CreateLsConfig() {
        LeaderstatsConfigFile = new File(pl.getDataFolder(), "leaderstats.yml");
        if (!LeaderstatsConfigFile.exists()) {
            LeaderstatsConfigFile.getParentFile().mkdirs();
            pl.saveResource("leaderstats.yml", false);
        }

        LeaderstatsConfig = new YamlConfiguration();
        try {
            LeaderstatsConfig.load(LeaderstatsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    /* User Edit:
        Instead of the above Try/Catch, you can also use
        YamlConfiguration.loadConfiguration(customConfigFile)
    */
    }

    public static void UpdateLeaderstats(Player player, String fish, double weight) {
        ConfigurationSection CurrentFishSection;
        if (!LeaderstatsConfig.getKeys(false).contains(fish)) {
            CurrentFishSection = LeaderstatsConfig.createSection(fish);

            CurrentFishSection.set("player", player.getName());
            CurrentFishSection.set("weight", weight);

            String prefix = "&7[&aMFish&7] &f";
            String out = prefix + "&aНовый рекорд";
            out += "\n" + RarityColors.getOrDefault(FishRarities.getOrDefault(fish, "common"), "&f") +
                    Translates.getOrDefault(fish, fish) + "&f | &7" + player.getName() + "&f | &7" + weight + " кг";
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', out));
        } else {
            CurrentFishSection = LeaderstatsConfig.getConfigurationSection(fish);

            if (CurrentFishSection.getDouble("weight") < weight) {
                CurrentFishSection.set("player", player.getName());
                CurrentFishSection.set("weight", weight);

                String prefix = "&7[&aMFish&7] &f";
                String out = prefix + "&aНовый рекорд";
                out += "\n" + RarityColors.getOrDefault(FishRarities.getOrDefault(fish, "common"), "&f") +
                        Translates.getOrDefault(fish, fish) + "&f | &7" + player.getName() + "&f | &7" + weight + " кг";
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', out));

            }
        }


        try {
            LeaderstatsConfig.save(LeaderstatsConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<Pair<String, Pair<String, Double>>> GetLsValues() {
        ArrayList<Pair<String, Pair<String, Double>>> LsValues = new ArrayList<>();
        for (String Fish : LeaderstatsConfig.getKeys(false)) {

            String PlayerName = LeaderstatsConfig.getString(Fish + ".player");
            double weight = LeaderstatsConfig.getDouble(Fish + ".weight");

            Pair<String, Pair<String, Double>> FishPair = Pair.of(Fish, Pair.of(PlayerName, weight));
            LsValues.add(FishPair);
        }
        LsValues.sort(new CompareFish());
        return LsValues;
    }
}
