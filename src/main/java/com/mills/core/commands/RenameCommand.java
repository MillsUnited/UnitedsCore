package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RenameCommand implements CommandExecutor {

    private final Set<String> validItems = new HashSet<>(Arrays.asList(
            "PICKAXE", "AXE", "SWORD", "HOE", "SHOVEL", "HELMET",
            "LEGGINGS", "CHESTPLATE", "BOOTS", "ELYTRA", "SHIELD",
            "MACE", "TRIDENT", "BOW", "CROSSBOW"
    ));

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;

        if (!player.hasPermission("server.rename")) {
            player.sendMessage(Main.prefix + "You don't have permission to use this command!");
            return false;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR) {
            player.sendMessage(Main.prefix + ChatColor.RED + "You're not holding an item!");
            return true;
        }

        String itemType = itemStack.getType().name();
        boolean valid = validItems.stream().anyMatch(itemType::contains);
        if (!valid) {
            player.sendMessage(Main.prefix + ChatColor.RED + "You cannot rename that item.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Main.prefix + ChatColor.RED + "Please enter a new name!");
            return true;
        }

        StringBuilder nameBuilder = new StringBuilder();
        for (String arg : args) {
            nameBuilder.append(arg).append(" ");
        }
        String name = nameBuilder.toString().trim();
        String notBoldName = name.replace("&l", "");

        ItemMeta meta = itemStack.getItemMeta();

        if (!player.hasPermission("server.rename.bold")) {
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', notBoldName));
            itemStack.setItemMeta(meta);
            player.sendMessage(Main.prefix + "you renamed your item to " + ChatColor.translateAlternateColorCodes('&', notBoldName));
            return true;
        }

        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        itemStack.setItemMeta(meta);
        player.sendMessage(Main.prefix + "you renamed your item to " + ChatColor.translateAlternateColorCodes('&', name));
        return true;
    }
}
