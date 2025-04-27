package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.heal")) {

                player.sendMessage(Main.prefix + "You have been healed!");
                player.setHealth(20);

            } else {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
            }
        }
        return false;
    }
}
