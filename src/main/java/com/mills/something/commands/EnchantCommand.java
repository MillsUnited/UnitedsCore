package com.mills.something.commands;

import com.mills.something.Main;
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

    private Main main;

    public EnchantCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.enchant")) {

                ItemStack item = player.getInventory().getItemInMainHand();

                List<Material> allowedItems = Arrays.asList(
                        Material.DIAMOND_AXE,
                        Material.DIAMOND_PICKAXE,
                        Material.DIAMOND_HOE
                );

                if (allowedItems.contains(item.getType())) {

                    if (args.length == 2) {
                        String enchantName = args[0].toUpperCase();
                        int enchantLevel;

                        try {
                            enchantLevel = Integer.parseInt(args[1]);
                        } catch (NumberFormatException e) {
                            player.sendMessage(main.prefix + main.getMessagesManager().getMessage("enchant.invalid-level"));
                            return true;
                        }

                        Enchantment enchantment = Enchantment.getByName(enchantName);

                        if (enchantment != null) {
                            item.addUnsafeEnchantment(enchantment, enchantLevel);
                            player.sendMessage(main.prefix + main.getMessagesManager().getMessage("enchant.success").replace("<enchant>", enchantName.toLowerCase())
                                    .replace("<level>", Integer.toString(enchantLevel)));
                        } else {
                            player.sendMessage(main.prefix + main.getMessagesManager().getMessage("enchant.invalid-enchant"));
                        }
                    } else {
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("enchant.invalid-command"));
                    }

                } else {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("enchant.invalid-item"));
                }

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("enchant.no-perm"));
            }
        } else {
            sender.sendMessage("Only players can use this command.");
        }

        return true;
    }
}