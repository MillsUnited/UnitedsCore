package com.mills.core.commands.teleport;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportHereCommand implements CommandExecutor {

    private String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lTeleport &8» &7");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.teleport")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            if (args.length == 0) {
                player.sendMessage(prefix + "Ussage: /tphere <player>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                player.sendMessage(prefix + "Player not found.");
                return false;
            }

            if (player == target) {
                player.sendMessage(prefix + "you cant teleport yourself!");
                return true;
            }

            target.teleport(player.getLocation());
            player.sendMessage(prefix + "teleported " + target.getName() + " to your location!");
        }

        return false;
    }
}
