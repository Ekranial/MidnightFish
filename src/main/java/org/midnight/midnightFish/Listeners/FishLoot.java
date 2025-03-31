package org.midnight.midnightFish.Listeners;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

import static org.midnight.midnightFish.MidnightFish.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.*;
import static org.midnight.midnightFish.Utils.ProcUtilities.Proc;

public class FishLoot implements Listener {
    @EventHandler
    public static void FishCatch(PlayerFishEvent event) {

        if (event.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            if (!event.getPlayer().getInventory().getItem(event.getHand()).getPersistentDataContainer().has(new NamespacedKey(pl, "specialrod"))) {
                return;
            }

            Player player = event.getPlayer();
            String biome = event.getHook().getLocation().getBlock().getBiome().toString();
//            player.sendMessage(biome);
            if (Proc(GarbageChance) || !Biomes.containsKey(biome)) {
                for (String Rarity : new ArrayList<String>(Arrays.asList("legendary", "epic", "rare"))) {
                    if (Proc(RarityChances.get(Rarity)) && !Garbage.get(Rarity).isEmpty()) {
                        GrantGarbageLoot(event, player, Rarity);
                        return;
                    }
                }
                GrantGarbageLoot(event, player, "common");
            } else {
                for (String Rarity : new ArrayList<String>(Arrays.asList("legendary", "epic", "rare"))) {
                    if (Proc(RarityChances.get(Rarity)) && !Biomes.get(biome).get(Rarity).isEmpty()) {
                        GrantFishLoot(event, biome, player, Rarity);
                        return;
                    }
                }
                GrantFishLoot(event, biome, player, "common");
            }
        }
    }

    public static void GrantGarbageLoot(PlayerFishEvent event, Player player, String rarity) {
        ArrayList<String> LootList = Garbage.get(rarity);
        Random random = new Random();
        String ItemName = LootList.get(random.nextInt(LootList.size()));

        ItemStack itemStack = new ItemStack(Material.IRON_NUGGET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(pl, "garbagedrop"), PersistentDataType.STRING, ItemName);
        itemMeta.setItemName(ChatColor.translateAlternateColorCodes('&',
                RarityColors.getOrDefault(rarity, "&f") +
                        Translates.getOrDefault(ItemName, ItemName)));
        itemStack.setItemMeta(itemMeta);

        Item item = (Item) event.getCaught();
        item.setItemStack(itemStack);
    }

    public static void GrantFishLoot(PlayerFishEvent event, String biome, Player player, String rarity) {
        ArrayList<String> LootList = Biomes.get(biome).get(rarity);
        Random random = new Random();
        String ItemName = LootList.get(random.nextInt(LootList.size()));

        ItemStack itemStack = new ItemStack(Material.IRON_NUGGET);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(pl, "fishdrop"), PersistentDataType.STRING, ItemName);
        itemMeta.setItemName(ChatColor.translateAlternateColorCodes('&',
                RarityColors.getOrDefault(rarity, "&f") +
                        Translates.getOrDefault(ItemName, ItemName)));
        Pair<String, String> WeightPair = GetFishWeight(biome, rarity, ItemName);
        itemMeta.setLore(Collections.singletonList((ChatColor.GRAY + WeightPair.left() + " кг")));
        int CustomModelData = GetFishModel(biome, rarity, ItemName, WeightPair.right());
        itemMeta.setCustomModelData(CustomModelData);

        itemStack.setItemMeta(itemMeta);

        UpdateLeaderstats(player, ItemName, Double.parseDouble(WeightPair.left()));

        Item item = (Item) event.getCaught();
        item.setItemStack(itemStack);
    }

    public static Pair<String, String> GetFishWeight(String biome, String fish_rarity, String fish) {
        for (String Rarity : new ArrayList<String>(Arrays.asList("big", "medium"))) {
            if (Proc(WeightChances.get(Rarity))) {
                double w1 = Double.parseDouble(pl.getConfig().getString("biomes." + biome + "." + fish_rarity +
                        "." + fish + "." + Rarity + "-weight-interval").split("-")[0]);
                double w2 = Double.parseDouble(pl.getConfig().getString("biomes." + biome + "." + fish_rarity +
                        "." + fish + "." + Rarity + "-weight-interval").split("-")[1]);

                DecimalFormat df = new DecimalFormat("#.###");
                df.setRoundingMode(RoundingMode.FLOOR);

                Random random = new Random();
                return Pair.of(df.format(random.nextDouble(w1, w2)), Rarity);
            }
        }
        String Rarity = "small";
        double w1 = Double.parseDouble(pl.getConfig().getString("biomes." + biome + "." + fish_rarity +
                "." + fish + "." + Rarity + "-weight-interval").split("-")[0]);
        double w2 = Double.parseDouble(pl.getConfig().getString("biomes." + biome + "." + fish_rarity +
                "." + fish + "." + Rarity + "-weight-interval").split("-")[1]);

        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.FLOOR);

        Random random = new Random();
        return Pair.of(df.format(random.nextDouble(w1, w2)), Rarity);
    }

    public static int GetFishModel(String biome, String fish_rarity, String fish, String weight_rarity) {
        ConfigurationSection CurrentFishSection = pl.getConfig().getConfigurationSection("biomes." + biome + "." + fish_rarity +
                "." + fish);
        if (!CurrentFishSection.contains(weight_rarity + "-model")) return 1;
        return (CurrentFishSection.getInt(weight_rarity + "-model"));
    }

    public static void UpdateLeaderstats(Player player, String fish, double weight) {
        ConfigurationSection CurrentFishSection;
        if (!LeaderstatsConfig.getKeys(false).contains(fish)) {
            CurrentFishSection = LeaderstatsConfig.createSection(fish);

            CurrentFishSection.set("player", player.getName());
            CurrentFishSection.set("weight", weight);

            String prefix = "&7[&aMFish&7] &f";
            String out = prefix + "&aНовый рекорд";
            out += "\n" + RarityColors.getOrDefault(FishRarities.getOrDefault(fish, "common"), "&f") +
                    Translates.getOrDefault(fish, fish) + "&f | &7" + player.getName() + "&f | &7" + weight + " кг";
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', out));
        } else {
            CurrentFishSection = LeaderstatsConfig.getConfigurationSection(fish);

            if (CurrentFishSection.getDouble("weight") < weight) {
                CurrentFishSection.set("player", player.getName());
                CurrentFishSection.set("weight", weight);

                String prefix = "&7[&aMFish&7] &f";
                String out = prefix + "&aНовый рекорд";
                out += "\n" + RarityColors.getOrDefault(FishRarities.getOrDefault(fish, "common"), "&f") +
                        Translates.getOrDefault(fish, fish) + "&f | &7" + player.getName() + "&f | &7" + weight + " кг";
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', out));

            }
        }


        try {
            LeaderstatsConfig.save(LeaderstatsConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
