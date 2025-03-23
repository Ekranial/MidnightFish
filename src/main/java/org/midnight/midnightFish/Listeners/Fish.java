package org.midnight.midnightFish.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Random;

import static org.midnight.midnightFish.MidnightFish.pl;
import static org.midnight.midnightFish.Utils.InitializeMobList.MobTypes;

public class Fish implements Listener {

    @EventHandler
    public static void FishCatch(PlayerFishEvent event) {
        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {

            int num = new Random().nextInt(100) + 1;
            if (!(1 <= num && num <= 3)) return;

            int MobTypeInd = new Random().nextInt(MobTypes.size());
            Entity entity = event.getPlayer().getWorld().spawnEntity(event.getHook().getLocation(), MobTypes.get(MobTypeInd));
            event.getCaught().addPassenger(entity);

            Item item = (Item) event.getCaught();
            item.getPersistentDataContainer().set(new NamespacedKey(pl, "fishedmob"), PersistentDataType.BOOLEAN, true);
            try {
                Bukkit.getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
                    @Override
                    public void run() {
                        event.getCaught().remove();
                    }
                }, 40);
            } catch (Exception ex) {
                ;
            }

        }
    }
}
