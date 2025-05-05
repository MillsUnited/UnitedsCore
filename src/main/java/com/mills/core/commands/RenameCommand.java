package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.Bukkit;
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

    private final int limit = 15;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (!player.hasPermission("server.rename")) {
            player.sendMessage(Main.prefix + ChatColor.RED + "You don't have permission to use this command!");
            return false;
        }

        ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType() == Material.AIR) {
            player.sendMessage(Main.prefix + ChatColor.RED + "You're not holding an item!");
            return true;
        }

        String itemType = itemStack.getType().name();
        boolean valid = validItems.stream().anyMatch(validName -> itemType.endsWith(validName));
        if (!valid) {
            player.sendMessage(Main.prefix + ChatColor.RED + "You cannot rename that item.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Main.prefix + ChatColor.RED + "Please enter a new name!");
            return true;
        }

        String name = String.join(" ", args).trim();
        String translatedName = ChatColor.translateAlternateColorCodes('&', name);
        String visibleName = ChatColor.stripColor(translatedName);

        if (visibleName.isBlank()) {
            player.sendMessage(Main.prefix + ChatColor.RED + "Name must include visible characters.");
            return true;
        }

        if (visibleName.length() > limit) {
            player.sendMessage(Main.prefix + ChatColor.RED + "Name is too long! Max " + limit + " visible characters.");
            return true;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null) {
            player.sendMessage(Main.prefix + ChatColor.RED + "This item cannot be renamed.");
            return true;
        }

        if (!player.hasPermission("server.rename.bold")) {
            String noBold = name.replace("&l", "");
            translatedName = ChatColor.translateAlternateColorCodes('&', noBold);
        }

        meta.setDisplayName(translatedName);
        itemStack.setItemMeta(meta);
        player.updateInventory();

        player.sendMessage(Main.prefix + "You renamed your item to " + translatedName);
        return true;
    }
}