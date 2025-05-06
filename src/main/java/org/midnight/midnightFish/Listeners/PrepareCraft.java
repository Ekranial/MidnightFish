package org.midnight.midnightFish.Listeners;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

import static org.midnight.midnightFish.MidnightFish.pl;

public class PrepareCraft implements Listener {

    @EventHandler
    public static void PrepareCraftEvent(PrepareItemCraftEvent event) {
        for (ItemStack itemStack : event.getInventory().getMatrix()) {
            if (itemStack != null) {
                if (itemStack.getPersistentDataContainer().has(new NamespacedKey(pl, "fishdrop")) ||
                        itemStack.getPersistentDataContainer().has(new NamespacedKey(pl, "garbagedrop")) ||
                        itemStack.getPersistentDataContainer().has(new NamespacedKey(pl, "treasuredrop"))) {
                    if (event.getInventory().getResult() != null) {
                        if (event.getInventory().getResult().getType().equals(Material.IRON_INGOT)) {
                            event.getInventory().setResult(new ItemStack(Material.AIR));
                            return;
                        }
                    }
                }
            }
        }
    }
}
