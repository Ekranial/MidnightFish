package org.midnight.midnightFish.Listeners;

import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import static org.midnight.midnightFish.MidnightFish.pl;

public class PickupFishedItem implements Listener {

    @EventHandler
    public static void PickupFishedItem(PlayerPickupItemEvent event) {
        if (event.getItem().getPersistentDataContainer().has(new NamespacedKey(pl, "fishedmob"))) {
            event.setCancelled(true);
            event.getItem().remove();
        }
    }
}
