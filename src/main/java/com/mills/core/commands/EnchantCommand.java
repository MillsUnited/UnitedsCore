package com.mills.core.commands;

import com.mills.core.Main;
import com.mills.core.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EnchantCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.enchant.admin")) {

                ItemStack item = player.getInventory().getItemInMainHand();

                List<Material> allowedItems = Arrays.asList(
                        // Tools
                        Material.WOODEN_AXE,
                        Material.WOODEN_PICKAXE,
                        Material.WOODEN_HOE,
                        Material.WOODEN_SHOVEL,
                        Material.STONE_AXE,
                        Material.STONE_PICKAXE,
                        Material.STONE_HOE,
                        Material.STONE_SHOVEL,
                        Material.IRON_AXE,
                        Material.IRON_PICKAXE,
                        Material.IRON_HOE,
                        Material.IRON_SHOVEL,
                        Material.GOLDEN_AXE,
                        Material.GOLDEN_PICKAXE,
                        Material.GOLDEN_HOE,
                        Material.GOLDEN_SHOVEL,
                        Material.DIAMOND_AXE,
                        Material.DIAMOND_PICKAXE,
                        Material.DIAMOND_HOE,
                        Material.DIAMOND_SHOVEL,
                        Material.NETHERITE_AXE,
                        Material.NETHERITE_PICKAXE,
                        Material.NETHERITE_HOE,
                        Material.NETHERITE_SHOVEL,

                        // Weapons
                        Material.WOODEN_SWORD,
                        Material.STONE_SWORD,
                        Material.IRON_SWORD,
                        Material.GOLDEN_SWORD,
                        Material.DIAMOND_SWORD,
                        Material.NETHERITE_SWORD,
                        Material.BOW,
                        Material.CROSSBOW,
                        Material.TRIDENT,

                        // Armor - Leather
                        Material.LEATHER_HELMET,
                        Material.LEATHER_CHESTPLATE,
                        Material.LEATHER_LEGGINGS,
                        Material.LEATHER_BOOTS,

                        // Armor - Chainmail
                        Material.CHAINMAIL_HELMET,
                        Material.CHAINMAIL_CHESTPLATE,
                        Material.CHAINMAIL_LEGGINGS,
                        Material.CHAINMAIL_BOOTS,

                        // Armor - Iron
                        Material.IRON_HELMET,
                        Material.IRON_CHESTPLATE,
                        Material.IRON_LEGGINGS,
                        Material.IRON_BOOTS,

                        // Armor - Gold
                        Material.GOLDEN_HELMET,
                        Material.GOLDEN_CHESTPLATE,
                        Material.GOLDEN_LEGGINGS,
                        Material.GOLDEN_BOOTS,

                        // Armor - Diamond
                        Material.DIAMOND_HELMET,
                        Material.DIAMOND_CHESTPLATE,
                        Material.DIAMOND_LEGGINGS,
                        Material.DIAMOND_BOOTS,

                        // Armor - Netherite
                        Material.NETHERITE_HELMET,
                        Material.NETHERITE_CHESTPLATE,
                        Material.NETHERITE_LEGGINGS,
                        Material.NETHERITE_BOOTS,

                        // Other armor
                        Material.TURTLE_HELMET,
                        Material.SHIELD
                );

                if (allowedItems.contains(item.getType())) {

                    if (args.length == 2) {
                        String enchantName = args[0].toUpperCase();
                        int enchantLevel;

                        try {
                            enchantLevel = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(Main.prefix + "Invalid level. Please provide a number.");
                            return true;
                        }

                        Enchantment enchantment = Enchantment.getByName(enchantName);

                        if (enchantment != null) {
                            item.addUnsafeEnchantment(enchantment, enchantLevel);
                            player.sendMessage(Main.prefix + "Enchanted your item with " + Utils.format(enchantName.toLowerCase()) + " " + enchantLevel);
                        } else {
                            player.sendMessage(Main.prefix + "This enchantment does not exist!");
                        }
                    } else {
                        player.sendMessage(Main.prefix + "/enchant <enchant> <level>");
                    }

                } else {
                    player.sendMessage(Main.prefix + "You must be holding an item that can be enchanted.");
                }

            } else {
                player.sendMessage(Main.prefix + "You do not have permission to use this command.");
            }
        } else {
            sender.sendMessage("Only players can use this command.");
        }

        return true;
    }
}