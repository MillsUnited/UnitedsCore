package com.mills.core.commands;

import com.mills.core.Main;
import com.mills.core.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SeenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.seen")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return true;
            }

            if (!player.isOnline()) {

                long seen = player.getLastPlayed();

                long currentTime = System.currentTimeMillis();
                long timeDifference = currentTime - seen;
                String timeAgo = Utils.formatTimeAgo(timeDifference);

                if(args.length == 0) {
                    player.sendMessage(Main.prefix + timeAgo);

                    return true;
                }

                String targetName = args[0];
                OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

                if (!target.hasPlayedBefore()) {
                    player.sendMessage(Main.prefix + targetName + " has never logging in!");

                    return true;
                }

                player.sendMessage(Main.prefix + targetName + " last logged in " + timeAgo + "!");

            } else {
                player.sendMessage(Main.prefix + "player is online!");
            }
        }

        return false;
    }
}
