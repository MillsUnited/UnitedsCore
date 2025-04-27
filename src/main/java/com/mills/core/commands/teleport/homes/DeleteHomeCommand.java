package com.mills.core.commands.teleport.homes;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.UUID;

public class DeleteHomeCommand implements CommandExecutor {

    private Main main;
    private HomesManager homesManager;

    public DeleteHomeCommand(Main main) {
        this.main = main;
        this.homesManager = this.main.getHomesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        Player player = (Player) sender;
        UUID playerUuid = player.getUniqueId();

        // Admin command: /delhome <player> <home>
        if (args.length == 2 && player.hasPermission(homesManager.admin)) {
            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            String home = args[1];

            if (!target.hasPlayedBefore()) {
                player.sendMessage(homesManager.prefix + "That player has never joined before!");
                return true;
            }

            UUID targetUuid = target.getUniqueId();
            List<String> targetHomeNames = homesManager.getHomeNames(targetUuid);

            if (!targetHomeNames.contains(home)) {
                player.sendMessage(homesManager.prefix + target.getName() + " doesn't have a home named '" + home + "'.");
                return true;
            }

            homesManager.deleteHome(targetUuid, home);
            player.sendMessage(homesManager.prefix + "Deleted home '" + home + "' for player " + target.getName() + ".");
            return true;
        }

        // Player command: /delhome <home>
        if (args.length == 1) {
            String home = args[0];

            List<String> playerHomes = homesManager.getHomeNames(playerUuid);
            if (!playerHomes.contains(home)) {
                player.sendMessage(homesManager.prefix + "You don't have a home named '" + home + "'.");
                return true;
            }

            homesManager.deleteHome(playerUuid, home);
            player.sendMessage(homesManager.prefix + "Deleted home '" + home + "'.");
            return true;
        }

        // Invalid usage
        player.sendMessage(homesManager + "Usage: /delhome <home>");
        if (player.hasPermission(homesManager.admin)) {
            player.sendMessage(homesManager + "/delhome <player> <home>");
        }

        return true;
    }
}
