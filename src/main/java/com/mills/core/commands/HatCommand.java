package com.mills.core.commands;

import com.mills.core.Main;
import com.mills.core.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HatCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage(Main.prefix + "Only players can use this command!");
            return true;
        }

        if (!player.hasPermission("server.hat")) {
            player.sendMessage(Main.prefix + "You don't have permission to use this command!");
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemStack helmet = player.getInventory().getHelmet();

        if (item.getType() == Material.AIR) {
            player.sendMessage(Main.prefix + "You don't have anything in your hand to set as your hat!");
            return true;
        }

        if (helmet != null && helmet.getType() != Material.AIR) {
            player.sendMessage(Main.prefix + "You must remove your current helmet first!");
            return true;
        }

        ItemStack newHelmet = item.clone();
        newHelmet.setAmount(1);

        player.getInventory().setHelmet(newHelmet);

        item.setAmount(item.getAmount() - 1);

        String itemName = Utils.format(newHelmet.getType().toString());
        player.sendMessage(Main.prefix + "Set " + itemName + " as your helmet!");

        return true;
    }
}
