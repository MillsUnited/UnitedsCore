package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.TropicalFish;
import org.jetbrains.annotations.NotNull;

public class TopCommand implements CommandExecutor {

    private Main main;

    public TopCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;


            Block block = player.getWorld().getHighestBlockAt(player.getLocation());
            Location blockLocation = block.getLocation();

            if (blockLocation.getY() > player.getLocation().getY()) {

                Location location = blockLocation.add(0.5, 1, 0.5);
                location.setDirection(player.getLocation().getDirection());
                player.teleport(location);
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("top.teleported"));
            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("top.invalid-blocks"));
            }
        }

        return false;
    }
}
