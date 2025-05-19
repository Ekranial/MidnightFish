package org.midnight.midnightFish.Listeners;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEnchant implements Listener {

    @EventHandler
    public static void PlayerEnchant(PrepareItemEnchantEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public static void PlayerAnvil(PrepareAnvilEvent event) {
        event.setResult(null);
    }

    @EventHandler
    public static void InvChange(PlayerInventorySlotChangeEvent event) {
        ItemStack item = event.getNewItemStack();

//        System.out.println(event.getSlot());

        if (item.getEnchantments() != null) {
            if (!item.getPersistentDataContainer().has(new NamespacedKey("midnightcore", "enchantment"))) {

                item.removeEnchantments();
                event.getPlayer().getInventory().setItem(event.getSlot(), item);
            }
        }

        
//        System.out.println(item1.getItemMeta().getPersistentDataContainer());
//        System.out.println(item2.getItemMeta().getPersistentDataContainer());
    }
}
