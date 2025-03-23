package org.midnight.midnightFish.Utils;

import org.bukkit.entity.EntityType;

import java.util.ArrayList;

public class InitializeMobList {

    public static ArrayList<EntityType> MobTypes = new ArrayList<>();

    public static void Init(){
        MobTypes.add(EntityType.PUFFERFISH);
        MobTypes.add(EntityType.SQUID);
        MobTypes.add(EntityType.DOLPHIN);
        MobTypes.add(EntityType.TURTLE);
        MobTypes.add(EntityType.GLOW_SQUID);
        MobTypes.add(EntityType.COW);
        MobTypes.add(EntityType.DROWNED);
        MobTypes.add(EntityType.ZOMBIE);
        MobTypes.add(EntityType.SKELETON);
        MobTypes.add(EntityType.SPIDER);
        MobTypes.add(EntityType.CREEPER);
        MobTypes.add(EntityType.FOX);
        MobTypes.add(EntityType.SLIME);
        MobTypes.add(EntityType.GUARDIAN);
        MobTypes.add(EntityType.AXOLOTL);
        MobTypes.add(EntityType.ELDER_GUARDIAN);
        MobTypes.add(EntityType.FROG);
        MobTypes.add(EntityType.PANDA);
    }
}
