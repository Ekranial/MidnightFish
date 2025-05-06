package org.midnight.midnightFish.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Merchant;
import org.bukkit.inventory.MerchantInventory;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static org.midnight.midnightFish.MidnightFish.pl;

public class TestCmd {

    public static void execute(Player player){
        if (!player.getName().equals("Ekran1al")) return;
        System.out.println("aboba");

        Merchant merchant = Bukkit.createMerchant("aboba");

        ItemStack itemStack = new ItemStack(Material.FISHING_ROD);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
        itemMeta.addEnchant(Enchantment.MENDING, 1, true);
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(pl, "specialrod"), PersistentDataType.STRING, "command");
        itemStack.setItemMeta(itemMeta);

        MerchantRecipe merchantRecipe = new MerchantRecipe(ItemStack.of(Material.DIAMOND), 999999);
        merchantRecipe.setIngredients(List.of(itemStack));

        merchant.setRecipes(List.of(merchantRecipe));
//        merchantRecipe.addIngredient(ItemStack.of(Material.GOLDEN_PICKAXE));
        player.openMerchant(merchant, false);

    }

}
