package org.midnight.midnightFish.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;

import static org.midnight.midnightFish.MidnightFish.pl;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.RarityColors;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.Translates;

public class MFishItem {
    private String Name;
    private String Rarity;
    private String CustomModelData;

    private MFishItem(String Name, String Rarity, String Model) {
        this.Name = Name;
        this.Rarity = Rarity;
        this.CustomModelData = Model;
    }

    private static NamespacedKey GetDefaultNamespacedKey() {
        return new NamespacedKey(pl, "item");
    }

    private ItemStack GetItemStack() {
        ItemStack itemStack = new ItemStack(Material.IRON_NUGGET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(pl, "item"), PersistentDataType.STRING, this.Name);
        itemMeta.setItemName(ChatColor.translateAlternateColorCodes('&',
                RarityColors.getOrDefault(this.Rarity, "&f") +
                        Translates.getOrDefault(this.Name, this.Name)));
{
            CustomModelDataComponent custom;
//        System.out.println(treasure.CustomModelData);
            if (this.CustomModelData != null) ModelDataComponent = itemMeta.getCustomModelDataComponent();
            customModelDataComponent.setStrings(Collections.singletonList(this.CustomModelData));
            itemMeta.setCustomModelDataComponent(customModelDataComponent);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }




}
