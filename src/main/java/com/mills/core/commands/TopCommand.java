package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.top")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return true;
            }

            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            Location blockLocation = block.getLocation();

            if (blockLocation.getY() > player.getLocation().getY()) {

                Location location = blockLocation.add(0.5, 1, 0.5);
                location.setDirection(player.getLocation().getDirection());
                player.teleport(location);
                player.sendMessage(Main.prefix + "teleported to highest block!");
            } else {
                player.sendMessage(Main.prefix + "their are no blocks above you!");
            }
        }

        return false;
    }
}
