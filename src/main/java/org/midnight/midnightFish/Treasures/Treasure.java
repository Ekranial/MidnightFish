package org.midnight.midnightFish.Treasures;

public class Treasure {
    public String Name;
    public String Rarity;
    public double DropChance;
    public int LvlReq;

    public Treasure(String name, String rarity, double DropChance, int LvlReq) {
        this.Name = name;
        this.Rarity = rarity;
        this.DropChance = DropChance;
        this.LvlReq = LvlReq;
    }
}
