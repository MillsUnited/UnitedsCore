package com.mills.core.commandTab;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EnchantTabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.hasPermission("server.enchant.admin")) {
            if (args.length == 1) {
                List<String> enchantments = new ArrayList<>();
                for (Enchantment enchantment : Enchantment.values()) {
                    enchantments.add(enchantment.getKey().getKey());
                }
                return StringUtil.copyPartialMatches(args[0], enchantments, new ArrayList<>());
            }
        }

        return new ArrayList<>();
    }
}
