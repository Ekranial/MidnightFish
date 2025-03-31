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
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.units.qual.C;
import org.jetbrains.annotations.NotNull;
import org.midnight.midnightFish.MidnightFish;
import org.midnight.midnightFish.Utils.Compare;
import org.midnight.midnightFish.Utils.InitializeConfigValues;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static org.midnight.midnightFish.MidnightFish.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.*;
import static org.midnight.midnightFish.Utils.ProcUtilities.Proc;

public class Mfish implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
//        if (!commandSender.isOp()) return false;

        String prefix = "&7[&aMFish&7] &f";
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
                    ArrayList<Pair<String, Pair<String, Double>>> LsValues = new ArrayList<>();
                    for (String Fish : LeaderstatsConfig.getKeys(false)) {

                        String PlayerName = LeaderstatsConfig.getString(Fish + ".player");
                        double weight = LeaderstatsConfig.getDouble(Fish + ".weight");

                        Pair<String, Pair<String, Double>> FishPair = Pair.of(Fish, Pair.of(PlayerName, weight));
                        LsValues.add(FishPair);
                    }
                    LsValues.sort(new Compare());

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
                case "test" -> {
                    if (!commandSender.getName().equals("Ekran1al")) return false;

//                    String out = "";
//                    for (String Fish : FishRarities.keySet()) {
//                        out += "\n&f" + Fish + RarityColors.getOrDefault(FishRarities.getOrDefault(Fish, "none"),
//                                "&f") + " " + Translates.getOrDefault(Fish, Fish);
//                    }
//                    commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', out));

                    Bukkit.getScheduler().scheduleAsyncDelayedTask(pl, () -> {
                        int num = Integer.parseInt(strings[2]);
                        int c = 0;
                        for(int i = 0; i <= num; i++) {
                            if (Proc(0.01)) {
                                c += 1;
//                                commandSender.sendMessage("Proced on i = " + i);
                            }
                        }
                        commandSender.sendMessage("Total tries: " + num +
                                "\nProced: " + c +
                                "\nChance: " + c / num);
                    }, 0);


                    return false;
                }
            }
        } else if (strings.length == 2 && strings[0].equals("test")) {
            if (!commandSender.getName().equals("Ekran1al")) return false;
            Bukkit.getScheduler().scheduleAsyncDelayedTask(pl, () -> {
                double num = Integer.parseInt(strings[1]);
                double c = 0;
                for(int i = 0; i <= num; i++) {
                    if (Proc(0.013)) {
                        c += 1;
//                                commandSender.sendMessage("Proced on i = " + i);
                    }
                }

                DecimalFormat df = new DecimalFormat("#.######");
                df.setRoundingMode(RoundingMode.FLOOR);

                commandSender.sendMessage("Total tries: " + num +
                        "\nProced: " + c +
                        "\nChance: " + df.format(c / num * 100));
            }, 0);


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
        System.out.println("aboba");
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));

        return false;
    }
}
