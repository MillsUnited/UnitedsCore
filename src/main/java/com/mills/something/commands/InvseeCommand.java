package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class InvseeCommand implements CommandExecutor {

    private Main main;

    public InvseeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.invsee")) {

                if (args.length == 0) {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("invsee.invalid-command"));

                    return true;
                }

                String targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);

                if (target == null) {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("invsee.invalid-player"));

                    return true;
                }

                if (target.isOnline()) {

                    if (sender == target) {
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("invsee.own-inventory"));
                        return true;
                    }

                    Inventory inv = Bukkit.createInventory(player, 54, targetName + "'s Inventory");

                    ItemStack panes = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
                    ItemMeta panemeta = panes.getItemMeta();
                    panemeta.setDisplayName("");
                    panes.setItemMeta(panemeta);

                    for (int i : new int[]{9,10,11,12,13,14,15,16,17}) {

                        inv.setItem(i, panes);

                    }

                    if (target.getInventory().getHelmet() != null) {
                        ItemStack helmet = new ItemStack(target.getInventory().getHelmet());
                        inv.setItem(0, helmet);
                    }

                    if (target.getInventory().getChestplate() != null) {
                        ItemStack chestplate = new ItemStack(target.getInventory().getChestplate());
                        inv.setItem(1, chestplate);
                    }

                    if (target.getInventory().getLeggings() != null) {
                        ItemStack leggings = new ItemStack(target.getInventory().getLeggings());
                        inv.setItem(2, leggings);
                    }

                    if (target.getInventory().getBoots() != null) {
                        ItemStack boots = new ItemStack(target.getInventory().getBoots());
                        inv.setItem(3, boots);
                    }

                    target.getInventory().getItemInOffHand();
                    ItemStack off = new ItemStack(target.getInventory().getItemInOffHand());
                    inv.setItem(4, off);

                    ItemStack[] items = target.getInventory().getContents();

                    for (int i = 0; i < 9; i++) {
                        if (items[i] != null) {
                            inv.setItem(i + 45, items[i]);
                        }
                    }

                    for (int i = 9; i < 36; i++) {
                        if (items[i] != null) {
                            inv.setItem(i + 9, items[i]);
                        }
                    }

                    player.openInventory(inv);
                }
            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("invsee.no-perm"));
            }
        }
        return false;
    }
}
