package com.mills.core.commands.teleport.homes;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class HomeTabComplete implements TabCompleter {

    private final HomesManager homesManager;

    public HomeTabComplete(HomesManager homesManager) {
        this.homesManager = homesManager;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();
        boolean isAdmin = sender.hasPermission(homesManager.admin);

        if (!(sender instanceof Player player)) return Collections.emptyList();

        String cmd = command.getName().toLowerCase();

        if (cmd.equals("home") || cmd.equals("delhome")) {
            if (args.length == 1) {
                if (isAdmin) {
                    for (String uuidStr : homesManager.getAllPlayersWithHomes()) {
                        try {
                            UUID uuid = UUID.fromString(uuidStr);
                            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                            if (offlinePlayer.getName() != null) {
                                suggestions.add(offlinePlayer.getName());
                            }
                        } catch (IllegalArgumentException ignored) {}
                    }
                    suggestions.addAll(homesManager.getHomeNames(player.getUniqueId()));
                } else {
                    suggestions.addAll(homesManager.getHomeNames(player.getUniqueId()));
                }

            } else if (args.length == 2 && isAdmin) {
                OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
                if (target != null && target.getName() != null) {
                    suggestions.addAll(homesManager.getHomeNames(target.getUniqueId()));
                }
            }

        } else if (cmd.equals("homes")) {
            if (args.length == 1 && isAdmin) {
                for (String uuidStr : homesManager.getAllPlayersWithHomes()) {
                    try {
                        UUID uuid = UUID.fromString(uuidStr);
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                        if (offlinePlayer.getName() != null) {
                            suggestions.add(offlinePlayer.getName());
                        }
                    } catch (IllegalArgumentException ignored) {}
                }
            }
        }

        return suggestions;
    }
}