package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PTimeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.ptime")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("day")) {

                    player.setPlayerTime(1000, false);
                    player.sendMessage(Main.prefix + "Changed time to Day!");

                } else if (args[0].equalsIgnoreCase("midnight")) {
                    player.setPlayerTime(18000, false);
                    player.sendMessage(Main.prefix + "Changed time to Mignight!");

                } else if (args[0].equalsIgnoreCase("night")) {
                    player.setPlayerTime(13000, false);
                    player.sendMessage(Main.prefix + "Changed time to Night!");

                } else if (args[0].equalsIgnoreCase("noon")) {
                    player.setPlayerTime(6000, false);
                    player.sendMessage(Main.prefix + "Changed time to Noon!");

                } else if (args[0].equalsIgnoreCase("reset")) {
                    player.resetPlayerTime();
                    player.sendMessage(Main.prefix + "reset player time!");
                }

            } else {
                player.sendMessage(Main.prefix + "/ptime <time>");
            }

        }
        return false;
    }
}
