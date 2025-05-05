package com.mills.core.commands.gamemode;

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

public class GamemodeTabComplete implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        switch (command.getName().toLowerCase()) {
            case "gamemode":
                if (sender.hasPermission("server.enchant.admin")) {
                    if (args.length == 1) {
                        return StringUtil.copyPartialMatches(args[0], Arrays.asList("creative", "survival", "spectator", "adventure"), new ArrayList<>());
                    }
                    if (args.length == 2) {
                        List<String> playername = new ArrayList<>();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            playername.add(player.getName());
                        }
                        return StringUtil.copyPartialMatches(args[1], playername, new ArrayList<>());
                    }
                }
                break;
            case "gmc":
                if (sender.hasPermission("server.gamemode")) {
                    if (args.length == 1) {
                        List<String> playername = new ArrayList<>();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            playername.add(player.getName());
                        }
                        return StringUtil.copyPartialMatches(args[0], playername, new ArrayList<>());
                    }
                }
                break;
            case "gms":
                if (sender.hasPermission("server.gamemode")) {
                    if (args.length == 1) {
                        List<String> playername = new ArrayList<>();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            playername.add(player.getName());
                        }
                        return StringUtil.copyPartialMatches(args[0], playername, new ArrayList<>());
                    }
                }
                break;
            case "gmsp":
                if (sender.hasPermission("server.gamemode")) {
                    if (args.length == 1) {
                        List<String> playername = new ArrayList<>();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            playername.add(player.getName());
                        }
                        return StringUtil.copyPartialMatches(args[0], playername, new ArrayList<>());
                    }
                }
                break;
            case "gma":
                if (sender.hasPermission("server.gamemode")) {
                    if (args.length == 1) {
                        List<String> playername = new ArrayList<>();
                        for (Player player : Bukkit.getOnlinePlayers()) {
                            playername.add(player.getName());
                        }
                        return StringUtil.copyPartialMatches(args[0], playername, new ArrayList<>());
                    }
                }
                break;
        }
        return List.of();
    }
}
