package com.mills.core.commands;

import com.mills.core.Main;
import com.mills.core.Utills;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FixCommand implements CommandExecutor {

    private static final Set<String> REPAIRABLE_ITEMS = new HashSet<>();

    static {
        REPAIRABLE_ITEMS.add("PICKAXE");
        REPAIRABLE_ITEMS.add("AXE");
        REPAIRABLE_ITEMS.add("SWORD");
        REPAIRABLE_ITEMS.add("HOE");
        REPAIRABLE_ITEMS.add("SHOVEL");
        REPAIRABLE_ITEMS.add("HELMET");
        REPAIRABLE_ITEMS.add("CHESTPLATE");
        REPAIRABLE_ITEMS.add("LEGGINGS");
        REPAIRABLE_ITEMS.add("BOOTS");
        REPAIRABLE_ITEMS.add("ELYTRA");
        REPAIRABLE_ITEMS.add("SHIELD");
        REPAIRABLE_ITEMS.add("MACE");
        REPAIRABLE_ITEMS.add("TRIDENT");
        REPAIRABLE_ITEMS.add("BOW");
        REPAIRABLE_ITEMS.add("CROSSBOW");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (player.hasPermission("server.fix") || player.hasPermission("server.fix")) {
                fixItemInHand(player);
            } else {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
            }
        } else if (args.length == 1 && args[0].equalsIgnoreCase("all")) {
            if (player.hasPermission("server.fix.all")) {
                fixAllItems(player);
            } else {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
            }
        } else {
            player.sendMessage(Main.prefix + ChatColor.RED + "Usage: /fix [all]");
        }

        return true;
    }

    private void fixItemInHand(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType().isAir()) {
            player.sendMessage(Main.prefix + ChatColor.RED + "You're not holding any item!");
            return;
        }

        if (isRepairable(item)) {
            Damageable meta = (Damageable) item.getItemMeta();
            if (meta != null && meta.hasDamage()) {
                meta.setDamage(0);
                item.setItemMeta(meta);
                player.sendMessage(Main.prefix + "Repaired " + ChatColor.RED + Utills.format(item.getType()));
            } else {
                player.sendMessage(Main.prefix + ChatColor.RED + "This item doesn't need repairing!");
            }
        } else {
            player.sendMessage(Main.prefix + ChatColor.RED + "You cannot fix this item!");
        }
    }

    private void fixAllItems(Player player) {
        StringBuilder repairedItems = new StringBuilder();
        int repairedCount = 0;

        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && !item.getType().isAir() && isRepairable(item)) {
                Damageable meta = (Damageable) item.getItemMeta();
                if (meta != null && meta.hasDamage()) {
                    meta.setDamage(0);
                    item.setItemMeta(meta);
                    repairedCount++;

                    if (repairedItems.length() > 0) {
                        repairedItems.append(ChatColor.GRAY).append(", ");
                    }
                    repairedItems.append(ChatColor.RED).append(Utills.format(item.getType()));
                }
            }
        }

        if (repairedCount > 0) {
            player.sendMessage(Main.prefix + ChatColor.GRAY + "Repaired: " + repairedItems.toString());
        } else {
            player.sendMessage(Main.prefix + ChatColor.RED + "You have no items that need repairing!");
        }
    }

    private boolean isRepairable(ItemStack item) {
        String typeName = item.getType().name();
        return REPAIRABLE_ITEMS.stream().anyMatch(typeName::endsWith);
    }
}
