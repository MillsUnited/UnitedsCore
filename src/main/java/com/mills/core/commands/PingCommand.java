package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.ping")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            int ping = player.getPing();

            if (player.isOnline()) {

                if (args.length == 0) {

                    player.sendMessage(Main.prefix + "Ping: " + ping);

                    return true;
                }

                String targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);

                int targetping = target.getPing();

                player.sendMessage(Main.prefix + targetName + "'s ping is " + targetping);

            } else {
                player.sendMessage(Main.prefix + "player is not online!");
            }

        }


        return false;
    }
}
