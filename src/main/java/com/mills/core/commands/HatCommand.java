package com.mills.core.commands;

import com.mills.core.Main;
import com.mills.core.Utills;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack item = player.getInventory().getItemInMainHand();
            ItemStack helmet = player.getInventory().getHelmet();
            String itemname = Utills.format(item.getType());

            if (!player.hasPermission("server.hat")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            if (item != null) {

                if (helmet == null) {

                    player.getInventory().setHelmet(item);
                    player.sendMessage(Main.prefix + "Set " + itemname + " to helmet!");
                    player.getInventory().remove(item);

                } else if (helmet != null) {

                    player.sendMessage(Main.prefix + "can't set helmet, take off helmet!");

                }

            } else {
                player.sendMessage(Main.prefix + "You are not holding anything!");
            }


        }


        return false;
    }
}
