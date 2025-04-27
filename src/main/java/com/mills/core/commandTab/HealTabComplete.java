package com.mills.core.commandTab;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HealTabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("server.heal")) {
            if (args.length == 1) {
                List<String> playername = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    playername.add(player.getName());
                }
                return StringUtil.copyPartialMatches(args[0], playername, new ArrayList<>());
            }
        }
        return new ArrayList<>();
    }
}
