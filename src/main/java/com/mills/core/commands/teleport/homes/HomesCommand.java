package com.mills.core.commands.teleport.homes;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class HomesCommand implements CommandExecutor {

    private Main main;
    private HomesManager homesManager;

    public HomesCommand(Main main) {
        this.main = main;
        this.homesManager = this.main.getHomesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (args.length == 0) {

                List<String> homeNames = homesManager.getHomeNames(uuid);

                if (homeNames.isEmpty()) {
                    player.sendMessage(homesManager.prefix + "You don't have any homes set.");
                } else {
                    String joined = String.join(", ", homeNames);
                    player.sendMessage(homesManager.prefix + joined);
                }
                return true;
            }

            if (player.hasPermission(homesManager.admin)) {
                if (args.length == 1) {
                    OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);

                    if (!target.hasPlayedBefore()) {
                        player.sendMessage(homesManager.prefix + "this player has never joined before!");
                        return true;
                    }

                    UUID targetUuid = target.getUniqueId();

                    List<String> targetHomeNames = homesManager.getHomeNames(targetUuid);

                    if (targetHomeNames.isEmpty()) {
                        player.sendMessage(homesManager.prefix + target.getName() + " doesn't have any homes set.");
                    } else {
                        String joined = String.join(", ", targetHomeNames);
                        player.sendMessage(homesManager.prefix + joined);
                    }
                }
            }
        }
        return false;
    }
}
