package org.midnight.midnightFish;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.midnight.midnightFish.Listeners.Fish;
import org.midnight.midnightFish.Listeners.PickupFishedItem;
import org.midnight.midnightFish.Utils.InitializeMobList;

public final class MidnightFish extends JavaPlugin {

    public static Plugin pl = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        pl = this;
        Bukkit.getPluginManager().registerEvents(new Fish(), this);
        Bukkit.getPluginManager().registerEvents(new PickupFishedItem(), this);

        InitializeMobList.Init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
