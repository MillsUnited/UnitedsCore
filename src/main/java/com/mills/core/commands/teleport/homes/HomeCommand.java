package com.mills.core.commands.teleport.homes;

import com.mills.core.Main;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HomeCommand implements CommandExecutor {

    private final Main main;
    private final HomesManager homesManager;

    public HomeCommand(Main main) {
        this.main = main;
        this.homesManager = this.main.getHomesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        // /home <home>
        if (args.length == 1) {
            String home = args[0];
            UUID uuid = player.getUniqueId();

            if (!homesManager.doesHomeExist(uuid, home)) {
                player.sendMessage(homesManager.prefix + "Home does not exist!");
                return true;
            }

            teleportToHome(player, uuid, home);
            player.sendMessage(homesManager.prefix + "Teleported to home " + home);
            return true;
        }

        // /home <player> <home>
        if (args.length == 2 && player.hasPermission(homesManager.admin)) {
            String targetName = args[0];
            String home = args[1];

            OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);
            if (target == null || target.getName() == null) {
                player.sendMessage(homesManager.prefix + "Player not found.");
                return true;
            }

            UUID targetUUID = target.getUniqueId();
            if (!homesManager.doesHomeExist(targetUUID, home)) {
                player.sendMessage(homesManager.prefix + "That player does not have a home named '" + home + "'.");
                return true;
            }

            teleportToHome(player, targetUUID, home);
            player.sendMessage(homesManager.prefix + "Teleported to " + target.getName() + "'s home '" + home + "'.");
            return true;
        }

        player.sendMessage(homesManager.prefix + ChatColor.RED + "Usage: /home <homeName> or /home <player> <homeName>");
        return true;
    }

    private void teleportToHome(Player player, UUID uuid, String home) {
        double x = homesManager.getHomeX(uuid, home);
        double y = homesManager.getHomeY(uuid, home);
        double z = homesManager.getHomeZ(uuid, home);
        float yaw = homesManager.getHomeYaw(uuid, home);
        float pitch = homesManager.getHomePitch(uuid, home);
        World world = homesManager.getHomeWorld(uuid, home);

        if (world == null) {
            player.sendMessage(homesManager.prefix + "World for home " + home + " not found.");
            return;
        }

        Location loc = new Location(world, x, y, z, yaw, pitch);
        player.teleport(loc);
    }
}
