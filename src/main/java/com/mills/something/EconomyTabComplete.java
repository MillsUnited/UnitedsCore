package com.mills.something;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EconomyTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // /economy <action> <player> <amount>

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("deposit", "withdraw"), new ArrayList<>());
        }

        if (args.length == 2) {
            List<String> playername = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                playername.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[1], playername, new ArrayList<>());
        }
        return List.of();
    }
}
