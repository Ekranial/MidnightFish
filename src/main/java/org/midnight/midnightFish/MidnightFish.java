package org.midnight.midnightFish;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.midnight.midnightFish.Commands.Mfish;
import org.midnight.midnightFish.Listeners.*;
import org.midnight.midnightFish.Utils.InitializeConfigValues;
import org.midnight.midnightFish.Utils.InitializeMobList;

import java.io.File;
import java.io.IOException;

import static org.midnight.midnightFish.Utils.Leaderstats.CreateLsConfig;
import static org.midnight.midnightFish.Utils.Levels.CreateLvlsConfig;

public final class MidnightFish extends JavaPlugin {

    public static Plugin pl = null;
    public static File LeaderstatsConfigFile;
    public static File LevelsConfigFile;
    public static FileConfiguration LeaderstatsConfig;
    public static FileConfiguration LevelsConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pl = this;

        pl.saveDefaultConfig();
        CreateLsConfig();
        CreateLvlsConfig();

        Bukkit.getPluginManager().registerEvents(new FishMobs(), this);
        Bukkit.getPluginManager().registerEvents(new PickupFishedItem(), this);
        Bukkit.getPluginManager().registerEvents(new FishLoot(), this);
        Bukkit.getPluginManager().registerEvents(new PrepareCraft(), this);
        Bukkit.getPluginManager().registerEvents(new TradeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(), this);

        Bukkit.getPluginCommand("mfish").setExecutor(new Mfish());

        InitializeMobList.Init();
        InitializeConfigValues.Init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
