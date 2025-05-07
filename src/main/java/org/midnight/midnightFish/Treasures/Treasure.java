package org.midnight.midnightFish.Treasures;

import javax.annotation.Nullable;

public class Treasure {
    public String Name;
    public String Rarity;
    public double DropChance;
    public int LvlReq;
    public String CustomModelData;

    public Treasure(String name, String rarity, double DropChance, int LvlReq, String Model) {
        this.Name = name;
        this.Rarity = rarity;
        this.DropChance = DropChance;
        this.LvlReq = LvlReq;
        this.CustomModelData = Model;
    }

    public Treasure(String name, String rarity, double DropChance, int LvlReq) {
        this.Name = name;
        this.Rarity = rarity;
        this.DropChance = DropChance;
        this.LvlReq = LvlReq;
        this.CustomModelData = null;
    }
}
