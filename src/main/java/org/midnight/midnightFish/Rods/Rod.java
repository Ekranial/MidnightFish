package org.midnight.midnightFish.Rods;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Biome;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.Arrays;

import static org.midnight.midnightFish.MidnightFish.pl;

public class Rod {
    public String Name;
    public String Rarity;
    public String CustomModelData;
    public ArrayList<Biome> Biomes;

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

        switch (PDDValue){
            case "command":
                this.Biomes.addAll(Arrays.asList(Biome.values()));
                break;
            case "Snowy":
                this.Biomes.add(Biome.SNOWY_PLAINS);
                this.Biomes.add(Biome.SNOWY_TAIGA);
                this.Biomes.add(Biome.FROZEN_RIVER);
                break;
            case "Taiga":
                this.Biomes.add(Biome.TAIGA);
                this.Biomes.add(Biome.OLD_GROWTH_PINE_TAIGA);
                this.Biomes.add(Biome.OLD_GROWTH_SPRUCE_TAIGA);
                this.Biomes.add(Biome.COLD_OCEAN);
                this.Biomes.add(Biome.DEEP_COLD_OCEAN);
                break;
            case "Plains":
                this.Biomes.add(Biome.PLAINS);
                this.Biomes.add(Biome.OCEAN);
                this.Biomes.add(Biome.DEEP_OCEAN);
                this.Biomes.add(Biome.LUKEWARM_OCEAN);
                this.Biomes.add(Biome.DEEP_LUKEWARM_OCEAN);
                break;
            case "Desert":
                this.Biomes.add(Biome.SAVANNA);
                this.Biomes.add(Biome.SAVANNA_PLATEAU);
                this.Biomes.add(Biome.WARM_OCEAN);
                this.Biomes.add(Biome.BADLANDS);
                this.Biomes.add(Biome.BEACH);
                break;
            case "Cave":
                this.Biomes.add(Biome.LUSH_CAVES);
                this.Biomes.add(Biome.DEEP_DARK);
                break;
            case "Mountain":
                this.Biomes.add(Biome.GROVE);
                this.Biomes.add(Biome.SNOWY_SLOPES);
                this.Biomes.add(Biome.FROZEN_PEAKS);
                this.Biomes.add(Biome.JAGGED_PEAKS);
                this.Biomes.add(Biome.MEADOW);
                break;
            case "Forest":
                this.Biomes.add(Biome.RIVER);
                this.Biomes.add(Biome.FOREST);
                this.Biomes.add(Biome.CHERRY_GROVE);
                this.Biomes.add(Biome.BIRCH_FOREST);
                this.Biomes.add(Biome.DARK_FOREST);
                this.Biomes.add(Biome.MANGROVE_SWAMP);
                this.Biomes.add(Biome.JUNGLE);
                break;
            case "End":
                this.Biomes.add(Biome.THE_END);
                this.Biomes.add(Biome.END_HIGHLANDS);
                break;
        }
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
