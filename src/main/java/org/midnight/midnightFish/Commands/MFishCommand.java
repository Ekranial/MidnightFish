package org.midnight.midnightFish.Commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import it.unimi.dsi.fastutil.Pair;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.midnight.midnightFish.Utils.InitializeConfigValues;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import static net.kyori.adventure.text.Component.*;
import static net.kyori.adventure.text.format.NamedTextColor.*;
import static org.midnight.midnightFish.MidnightFish.*;
import static org.midnight.midnightFish.Utils.InitializeConfigValues.*;
import static org.midnight.midnightFish.Utils.Leaderstats.GetLsValues;
import static org.midnight.midnightFish.Utils.Levels.GetPlrLvl;

public class MFishCommand {
    // Всякие литералы (готовые строки внутри кода) лучше убирать из функций куда-то в константы
    // Так их проще будет редактировать
    // Чтобы не создавать кучу переменных, их выносят в словарь
    private static final Map<String, String> PREFIXES = Map.of(
            "name", "mfish",
            "reload", "reload",
            "special", "sp",
            "leaderStats", "ls",
            "level", "lvl"
    );

    private static final Map<String, Component> MESSAGES = Map.of(
        "playerUsage", text()
                .append(text("[", GRAY))
                .append(text("MFISH", GREEN))
                .append(text("] ", GRAY))
                .append(text("Доступные команды:\n", WHITE))

                .append(text("/" + PREFIXES.get("name") + " " + PREFIXES.get("leaderStats"), GOLD))
                .append(text("\nУзнать списки лидеров", GREEN))

                .append(text("/" + PREFIXES.get("name") + " " + PREFIXES.get("level"), GOLD))
                .append(text("\nУзнать ваш уровень и опыт", GREEN))
            .build(),
            "operatorUsage", text()
                .append(text("[", GRAY))
                .append(text("MFISH", GREEN))
                .append(text("] ", GRAY))
                .append(text("Доступные команды:", WHITE))

                .append(text("\n/" + PREFIXES.get("name") + " " + PREFIXES.get("leaderStats"), GOLD))
                .append(text(" → Узнать списки лидеров", GREEN))

                .append(text("\n/" + PREFIXES.get("name") + " " + PREFIXES.get("level"), GOLD))
                .append(text(" → Узнать ваш уровень и опыт", GREEN))

                .append(text("\n/" + PREFIXES.get("name") + " " + PREFIXES.get("reload"), GOLD))
                .append(text(" → Перезапустить конфигурацию плагина", GREEN))

                .append(text("\n/" + PREFIXES.get("name") + " " + PREFIXES.get("special"), GOLD))
                .append(text(" → Сделать удочку специальной", GREEN))
            .build(),
            "pluginReloaded", text()
                .append(text("[", GRAY))
                .append(text("MFISH", GREEN))
                .append(text("] ", GRAY))
                .append(text("Конфигурация перезагружена", GREEN))
            .build(),
            "rodSpecialized", text()
                .append(text("Теперь ваша удочка ", GREEN))
                .append(text("специальная", GOLD))
            .build(),
            "scoreHeader", text()
                .append(text("Таблица лидеров", GREEN))
            .build(),
            "emptyScore", text()
                .append(text("Гномы хуекрады украли таблицу", GREEN))
            .build()
    );

    // Вместо ужасного огромного switch-case, древо в нативном стиле от ядра
    public static LiteralCommandNode<CommandSourceStack> createCommand() {
        // /mfish
        LiteralArgumentBuilder<CommandSourceStack> root = Commands.literal(PREFIXES.get("name"))
            .executes(MFishCommand::usage);

        // /mfish reload
        root.then(Commands.literal(PREFIXES.get("reload"))
            .requires(sender -> sender.getSender().isOp())
            .executes(MFishCommand::reload));

        // /mfish sp
        root.then(Commands.literal(PREFIXES.get("special"))
            .requires(sender -> sender.getSender().isOp())
            .executes(MFishCommand::special));

        // /mfish ls
        root.then(Commands.literal(PREFIXES.get("leaderStats"))
            .executes(MFishCommand::leaderStats));

        // /mfish lvl
        root.then(Commands.literal(PREFIXES.get("level"))
            .executes(MFishCommand::lvl));

        return root.build();
    }

    // Команды тоже раскиданы по методам, чтобы проще было редачить
    public static int usage(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        sender.sendMessage(MESSAGES.get(sender.isOp() ? "operatorUsage" : "playerUsage"));
        return Command.SINGLE_SUCCESS;
    }

    public static int reload(CommandContext<CommandSourceStack> context) {
        pl.reloadConfig();
        try {
            LeaderstatsConfig.load(LeaderstatsConfigFile);
            LevelsConfig.load(LevelsConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        CommandSender sender = context.getSource().getSender();

        InitializeConfigValues.Init();
        sender.sendMessage(MESSAGES.get("pluginReloaded"));
        Bukkit.getLogger().info("[MidnightFish] Config reloaded");
        return Command.SINGLE_SUCCESS;
    }

    public static int special(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        Player player = (Player) sender;

        ItemStack itemStack = player.getItemInHand();
        if (itemStack == null) return 0;

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
        persistentDataContainer.set(new NamespacedKey(pl, "specialrod"), PersistentDataType.STRING, "command");
        itemStack.setItemMeta(itemMeta);
        player.setItemInHand(itemStack);
        player.sendMessage(MESSAGES.get("rodSpecialized"));
        return Command.SINGLE_SUCCESS;
    }

    public static int leaderStats(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        ArrayList<Pair<String, Pair<String, Double>>> LsValues = GetLsValues();
        sender.sendMessage(MESSAGES.get("scoreHeader"));
        if (LsValues.isEmpty()) {
            sender.sendMessage(MESSAGES.get("emptyScore"));
            return Command.SINGLE_SUCCESS;
        }

        TextComponent.Builder output = text();

        for (Pair<String, Pair<String, Double>> FishPair : LsValues) {
            String Fish = FishPair.left();
            String PlayerName = FishPair.right().left();
            double Weight = FishPair.right().right();

            output.append(text("\n"))
                .append(text(Translates.getOrDefault(Fish, Fish))) // Цвета надо позже дописать
                .append(text(" | ", WHITE))
                .append(text(PlayerName, GREEN))
                .append(text(" | ", WHITE))
                .append(text(Weight + " кг", GREEN));
        }
        sender.sendMessage(output.build());
        return Command.SINGLE_SUCCESS;
    }

    public static int lvl(CommandContext<CommandSourceStack> context) {
        CommandSender sender = context.getSource().getSender();
        Pair<Integer, Double> LvlExp = GetPlrLvl(sender.getName());
        Component output = text()
            .append(text("[", GRAY))
            .append(text("MFISH", GREEN))
            .append(text("] ", GRAY))
            .append(text("Ваши статы:\n", WHITE))
            .append(text("Уровень ", GREEN))
            .append(text("[", GRAY))
            .append(text(LvlExp.first(), GOLD, TextDecoration.BOLD))
            .append(text("]\n", GRAY))
            .append(text("Опыт ", GREEN))
            .append(text("[", GRAY))
            .append(text(LvlExp.second(), GOLD, TextDecoration.BOLD))
            .append(text("]\n", GRAY))
        .build();
        sender.sendMessage(output);
        return Command.SINGLE_SUCCESS;
    }
}
