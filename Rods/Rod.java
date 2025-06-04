package org.midnight.midnightFish.Rods;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.*;

import static org.midnight.midnightFish.MidnightFish.LeaderstatsConfigFile;
import static org.midnight.midnightFish.MidnightFish.pl;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.FishRarities;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.GetDefaultRaritiesHashMap;

public class Rod {
    public String Name;
    private String Rarity;
    private String CustomModelData;
    private ArrayList<Biome> Biomes;

    public Rod(String Name, String Rarity, String Model) {
        this.Name = Name;
        this.Rarity = Rarity;
        this.CustomModelData = Model;
    }

    public Rod(String PDDValue) {
        
        this.Name = PDDValue;
        this.Rarity = null;
        this.CustomModelData = null;
        this.Biomes = new ArrayList<>();
        Map<String, List<Biome>> BIOME_MAPPINGS = Map.of(
                "command", Arrays.asList(Biome.values()),
                "Snowy", List.of(Biome.SNOWY_PLAINS, Biome.SNOWY_TAIGA, Biome.FROZEN_RIVER),
                "Taiga", List.of(Biome.TAIGA, Biome.OLD_GROWTH_PINE_TAIGA, Biome.OLD_GROWTH_SPRUCE_TAIGA, Biome.COLD_OCEAN, Biome.DEEP_COLD_OCEAN),
                "Plains", List.of(Biome.PLAINS, Biome.OCEAN, Biome.DEEP_OCEAN, Biome.LUKEWARM_OCEAN, Biome.DEEP_LUKEWARM_OCEAN),
                "Desert", List.of(Biome.SAVANNA, Biome.SAVANNA_PLATEAU, Biome.WARM_OCEAN, Biome.BADLANDS, Biome.BEACH),
                "Cave", List.of(Biome.LUSH_CAVES, Biome.DEEP_DARK),
                "Mountain", List.of(Biome.GROVE, Biome.SNOWY_SLOPES, Biome.FROZEN_PEAKS, Biome.JAGGED_PEAKS, Biome.MEADOW),
                "Forest", List.of(Biome.RIVER, Biome.FOREST, Biome.CHERRY_GROVE, Biome.BIRCH_FOREST, Biome.DARK_FOREST, Biome.MANGROVE_SWAMP, Biome.JUNGLE),
                "End", List.of(Biome.THE_END, Biome.END_HIGHLANDS));
    }

    public boolean IsBiomeCorrect(Biome biome) {
        if (this.Name.equals("command")) return true;
        System.out.println(biome);
        System.out.println(this.Biomes);
        System.out.println(this.Name);
        return this.Biomes.contains(biome);
    }

    public ItemStack GetRodItemStack() {

        ItemStack itemStack = ItemStack.of(Material.FISHING_ROD);

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(pl, "specialrod"), PersistentDataType.STRING, this.Name);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
