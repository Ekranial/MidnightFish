package org.midnight.midnightFish;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.midnight.midnightFish.Commands.Mfish;
import org.midnight.midnightFish.Listeners.FishLoot;
import org.midnight.midnightFish.Listeners.FishMobs;
import org.midnight.midnightFish.Listeners.PickupFishedItem;
import org.midnight.midnightFish.Utils.InitializeConfigValues;
import org.midnight.midnightFish.Utils.InitializeMobList;

import java.io.File;
import java.io.IOException;

public final class MidnightFish extends JavaPlugin {

    public static Plugin pl = null;
    public static File LeaderstatsConfigFile;
    public static FileConfiguration LeaderstatsConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pl = this;

        pl.saveDefaultConfig();
        createCustomConfig();

        Bukkit.getPluginManager().registerEvents(new FishMobs(), this);
        Bukkit.getPluginManager().registerEvents(new PickupFishedItem(), this);
        Bukkit.getPluginManager().registerEvents(new FishLoot(), this);

        Bukkit.getPluginCommand("mfish").setExecutor(new Mfish());

        InitializeMobList.Init();
        InitializeConfigValues.Init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void createCustomConfig() {
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
}
