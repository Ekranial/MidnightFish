package org.midnight.midnightFish.Items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;

import java.util.Collections;

import static org.midnight.midnightFish.MidnightFish.pl;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.RarityColors;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.Translates;

public class MFishItem {
    public String Name;
    public String Rarity;
    public String CustomModelData;

    public MFishItem(String Name, String Rarity, String Model) {
        this.Name = Name;
        this.Rarity = Rarity;
        this.CustomModelData = Model;
    }

    public static NamespacedKey GetDefaultNamespacedKey() {
        return new NamespacedKey(pl, "item");
    }

    public ItemStack GetItemStack() {
        ItemStack itemStack = new ItemStack(Material.IRON_NUGGET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(pl, "item"), PersistentDataType.STRING, this.Name);
        itemMeta.setItemName(ChatColor.translateAlternateColorCodes('&',
                RarityColors.getOrDefault(this.Rarity, "&f") +
                        Translates.getOrDefault(this.Name, this.Name)));

//        System.out.println(treasure.CustomModelData);
        if (this.CustomModelData != null) {
            CustomModelDataComponent customModelDataComponent = itemMeta.getCustomModelDataComponent();
            customModelDataComponent.setStrings(Collections.singletonList(this.CustomModelData));
            itemMeta.setCustomModelDataComponent(customModelDataComponent);
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
