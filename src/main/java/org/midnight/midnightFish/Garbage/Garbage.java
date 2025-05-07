package org.midnight.midnightFish.Garbage;

public class Garbage {
    public String Name;
    public String Rarity;
    public String CustomModelData;

    public Garbage(String Name, String Rarity, String Model) {
        this.Name = Name;
        this.Rarity = Rarity;
        this.CustomModelData = Model;
    }

    public Garbage(String Name, String Rarity) {
        this.Name = Name;
        this.Rarity = Rarity;
        this.CustomModelData = null;
    }
}
