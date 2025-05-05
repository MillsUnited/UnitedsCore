package com.mills.core.commands.teleport;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TeleportCommand implements CommandExecutor {

    private String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lTeleport &8» &7");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player && !(sender.hasPermission("server.teleport"))) {
            sender.sendMessage(Main.prefix + "You don't have permission to use this command!");
            return false;
        }

        if (args.length == 0 || args.length > 6 && sender instanceof Player) {
            sender.sendMessage(prefix + "Usage: /tp [<player>|<x> <y> <z>] [<player>|<x> <y> <z>]");
            return true;
        }

        // /tp <player>
        if (args.length == 1) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {

                if (player == target) {
                    player.sendMessage(prefix + "you cant teleport yourself!");
                    return true;
                }

                player.teleport(target.getLocation());
                player.sendMessage(prefix + "Teleported to " + target.getName());
            } else {
                player.sendMessage(prefix + "Player not found.");
            }
            return true;
        }

        // /tp <player> <player>
        if (args.length == 2) {
            Player p1 = Bukkit.getPlayer(args[0]);
            Player p2 = Bukkit.getPlayer(args[1]);
            if (p1 != null && p2 != null) {

                if (p1 == p2) {
                    p1.sendMessage(prefix + "you cant teleport yourself!");
                    return true;
                }

                p1.teleport(p2.getLocation());
                sender.sendMessage(prefix + "Teleported " + p1.getName() + " to " + p2.getName());
            } else {
                sender.sendMessage(prefix + "Player(s) not found.");
            }
        }

        // /tp <x> <y> <z> or /tp <x> <y> <z> <yaw> <pitch>
        if ((args.length == 3 || args.length == 5) && sender instanceof Player) {
            Player player = (Player) sender;
            try {
                double x = parseCoordinate(args[0], player.getLocation().getX());
                double y = parseCoordinate(args[1], player.getLocation().getY());
                double z = parseCoordinate(args[2], player.getLocation().getZ());
                float yaw = args.length == 5 ? Float.parseFloat(args[3]) : player.getLocation().getYaw();
                float pitch = args.length == 5 ? Float.parseFloat(args[4]) : player.getLocation().getPitch();

                Location loc = new Location(player.getWorld(), x, y, z, yaw, pitch);
                player.teleport(loc);
                player.sendMessage(prefix + "Teleported to coordinates.");
            } catch (NumberFormatException e) {
                player.sendMessage(prefix + "Invalid coordinates.");
            }
            return true;
        }

        // /tp <player> <x> <y> <z> or /tp <player> <x> <y> <z> <yaw> <pitch>
        if (args.length == 4 || args.length == 6) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(prefix + "Player not found.");
                return true;
            }

            if (sender == target) {
                sender.sendMessage(prefix + "you cant teleport yourself!");
                return true;
            }

            try {
                double x = parseCoordinate(args[1], target.getLocation().getX());
                double y = parseCoordinate(args[2], target.getLocation().getY());
                double z = parseCoordinate(args[3], target.getLocation().getZ());
                float yaw = args.length == 6 ? Float.parseFloat(args[4]) : target.getLocation().getYaw();
                float pitch = args.length == 6 ? Float.parseFloat(args[5]) : target.getLocation().getPitch();

                Location loc = new Location(target.getWorld(), x, y, z, yaw, pitch);
                target.teleport(loc);
                sender.sendMessage(prefix + "Teleported " + target.getName() + " to coordinates.");
            } catch (NumberFormatException e) {
                sender.sendMessage(prefix + "Invalid coordinates.");
            }
            return true;
        }


        return false;
    }

    private double parseCoordinate(String input, double current) throws NumberFormatException {
        if (input.startsWith("~")) {
            if (input.length() == 1) {
                return current;
            } else {
                return current + Double.parseDouble(input.substring(1));
            }
        } else {
            return Double.parseDouble(input);
        }
    }
}
