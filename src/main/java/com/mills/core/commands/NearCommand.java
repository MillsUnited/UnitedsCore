package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.near")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            StringBuilder builder = new StringBuilder();
            builder.append(Main.prefix);

            List<String> nearbyPlayerNames = new ArrayList<>();

            for (Entity near : player.getWorld().getNearbyEntities(player.getLocation(), 50, 50, 50)) {
                if (near instanceof Player nearPlayer) {
                    nearbyPlayerNames.add(nearPlayer.getName());
                }
            }

            builder.append(String.join(", ", nearbyPlayerNames));

            player.sendMessage(builder.toString());
        }

        return false;
    }
}
