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
import org.midnight.midnightFish.Treasures.Treasure;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

import static org.midnight.midnightFish.MidnightFish.pl;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.Treasures;
import static org.midnight.midnightFish.Utils.Levels.GetPlrLvl;
import static org.midnight.midnightFish.Utils.ProcUtils.Proc;

public class TestCmd {

    public static void execute(Player player){
        if (!player.getName().equals("Ekran1al")) return;

        Bukkit.getScheduler().scheduleAsyncDelayedTask(pl, () -> {
            double num = 1000000;
            double c = 0;
            for(int i = 0; i < num; i++) {
                for (Treasure treasure : Treasures) {
                    if (Proc(treasure.DropChance) && GetPlrLvl(player.getName()).first() >= treasure.LvlReq) {
                        c += 1;
                        break;
                    }
                }
            }

            DecimalFormat df = new DecimalFormat("#.######");
            df.setRoundingMode(RoundingMode.FLOOR);

            player.sendMessage("Total tries: " + num +
                    "\nProced: " + c +
                    "\nChance: " + df.format(c / num * 100));
        }, 0);

//        Merchant merchant = Bukkit.createMerchant("aboba");
//
//        ItemStack itemStack = new ItemStack(Material.FISHING_ROD);
//
//        ItemMeta itemMeta = itemStack.getItemMeta();
//        itemMeta.addEnchant(Enchantment.UNBREAKING, 3, true);
//        itemMeta.addEnchant(Enchantment.MENDING, 1, true);
//        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
//        persistentDataContainer.set(new NamespacedKey(pl, "specialrod"), PersistentDataType.STRING, "command");
//        itemStack.setItemMeta(itemMeta);
//
//        MerchantRecipe merchantRecipe = new MerchantRecipe(ItemStack.of(Material.DIAMOND), 999999);
//        merchantRecipe.setIngredients(List.of(itemStack));
//
//        merchant.setRecipes(List.of(merchantRecipe));
////        merchantRecipe.addIngredient(ItemStack.of(Material.GOLDEN_PICKAXE));
//        player.openMerchant(merchant, false);

    }

}
