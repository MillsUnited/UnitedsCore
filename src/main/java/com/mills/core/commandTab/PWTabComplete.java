package com.mills.core.commandTab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PWTabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("pweather")) {
            if (args.length == 1) {
                return StringUtil.copyPartialMatches(args[0], Arrays.asList("clear", "downfall", "reset"), new ArrayList<>());
            }
        }
        return new ArrayList<>();
    }
}
