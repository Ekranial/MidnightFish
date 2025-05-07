package org.midnight.midnightFish.Commands;

import it.unimi.dsi.fastutil.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.midnight.midnightFish.Utils.InitializeConfigValues;

import java.io.IOException;
import java.util.ArrayList;

import static org.midnight.midnightFish.MidnightFish.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.*;
import static org.midnight.midnightFish.Utils.Leaderstats.GetLsValues;
import static org.midnight.midnightFish.Utils.Levels.GetPlrLvl;

public class Mfish implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        if (!commandSender.isOp()) return false;

        String prefix = "&7[&aMFish&7] &f";

        if (strings.length == 0) {
            String msg = prefix + "Доступные команды";
            if (commandSender.isOp()) {
                msg += "\n&6/mfish reload";
                msg += "\n&aПерезагрузить конфигурацию плагина";

                msg += "\n&6/mfish sp";
                msg += "\n&aСделать удочку в руке &6специальной";
            }
            msg += "\n&6/mfish ls";
            msg += "\n&aУзнать списки лидеров";

            msg += "\n&6/mfish lvl";
            msg += "\n&aУзнать ваш уровень и опыт";
//            System.out.println("aboba");
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));

            return false;
        }

        if (strings.length == 1) {
            switch (strings[0]) {
                case "sp" -> {
                    if (!commandSender.isOp()) return false;
                    Player player = (Player) commandSender;

                    ItemStack itemStack = player.getItemInHand();
                    if (itemStack == null) return false;

                    ItemMeta itemMeta = itemStack.getItemMeta();
                    PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
                    persistentDataContainer.set(new NamespacedKey(pl, "specialrod"), PersistentDataType.STRING, "command");
                    itemStack.setItemMeta(itemMeta);

                    player.setItemInHand(itemStack);

                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aТеперь ваша удочка &6специальная"));
                    return false;
                }
                case "reload" -> {
                    if (!commandSender.isOp()) return false;
                    pl.reloadConfig();

                    try {
                        LeaderstatsConfig.load(LeaderstatsConfigFile);
                        LevelsConfig.load(LevelsConfigFile);
                    } catch (IOException | InvalidConfigurationException e) {
                        throw new RuntimeException(e);
                    }

                    InitializeConfigValues.Init();
                    Player player = (Player) commandSender;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&aКонфигурация перезагружена"));
                    Bukkit.getLogger().info("[MidnightFish] Config reloaded");
                    return false;
                }
                case "leaderstats", "ls" -> {
                    Player player = (Player) commandSender;

                    String out = prefix + "&aТаблица лидеров";
                    ArrayList<Pair<String, Pair<String, Double>>> LsValues = GetLsValues();

                    if (LsValues.isEmpty()) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + "&cНихуя нет"));
                        return false;
                    }

                    for (Pair<String, Pair<String, Double>> FishPair : LsValues) {
                        String Fish = FishPair.left();
                        String PlayerName = FishPair.right().left();
                        double Weight = FishPair.right().right();

                        out += "\n" + RarityColors.getOrDefault(FishRarities.getOrDefault(Fish, "common"), "&f") +
                                Translates.getOrDefault(Fish, Fish) + "&f | &7" + PlayerName + "&f | &7" + Weight + " кг";
                    }
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', out));

                    return false;
                }
                case "lvl", "level" -> {

                    Pair<Integer, Double> LvlExp = GetPlrLvl(commandSender.getName());
                    String out = prefix + "Ваши статы"
                            + "\n&aУровень &7[&6&l" + LvlExp.first() + "&f&7]"
                            + "\n&aОпыт &7[&6&l" + LvlExp.second() + "&f&7]";
                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', out));

                    return false;
                }
//                case "test" -> {
//                    if (!commandSender.getName().equals("Ekran1al")) return false;
//
////                    String out = "";
////                    for (String Fish : FishRarities.keySet()) {
////                        out += "\n&f" + Fish + RarityColors.getOrDefault(FishRarities.getOrDefault(Fish, "none"),
////                                "&f") + " " + Translates.getOrDefault(Fish, Fish);
////                    }
////                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', out));
//
//                    Bukkit.getScheduler().scheduleAsyncDelayedTask(pl, () -> {
//                        int num = Integer.parseInt(strings[2]);
//                        int c = 0;
//                        for(int i = 0; i <= num; i++) {
//                            if (Proc(0.01)) {
//                                c += 1;
////                                commandSender.sendMessage("Proced on i = " + i);
//                            }
//                        }
//                        commandSender.sendMessage("Total tries: " + num +
//                                "\nProced: " + c +
//                                "\nChance: " + c / num);
//                    }, 0);
//
//
//                    return false;
//                }
            }
        }
        if (strings[0].equals("test")) {

            TestCmd.execute((Player) commandSender);

            return false;
        }

        String msg = prefix + "Доступные команды";
        if (commandSender.isOp()) {
            msg += "\n&6/mfish reload";
            msg += "\n&aПерезагрузить конфигурацию плагина";

            msg += "\n&6/mfish sp";
            msg += "\n&aСделать удочку в руке &6специальной";
        }
        msg += "\n&6/mfish ls";
        msg += "\n&aУзнать списки лидеров";

        msg += "\n&6/mfish lvl";
        msg += "\n&aУзнать ваш уровень и опыт";
        System.out.println("aboba");
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));

        return false;
    }
}
