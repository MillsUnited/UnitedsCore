package com.mills.something.commands;

import com.mills.something.Main;
import com.mills.something.Utills;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FixCommand implements CommandExecutor {

    private Main main;

    public FixCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            List<String> stack = new ArrayList<>();
            stack.add("PICKAXE");
            stack.add("AXE");
            stack.add("SWORD");
            stack.add("HOE");
            stack.add("SHOVEL");
            stack.add("HELMET");
            stack.add("LEGGINGS");
            stack.add("CHESTPLATE");
            stack.add("LEGGINGS");
            stack.add("BOOTS");
            stack.add("ELYTRA");
            stack.add("SHEILD");
            stack.add("MACE");
            stack.add("TRIDENT");
            stack.add("BOW");
            stack.add("CROSSBOW");

            if (stack.stream().anyMatch(material -> player.getInventory().getItemInMainHand().getType().name().endsWith(material))) {

                ItemStack item = player.getInventory().getItemInMainHand();
                item.setDurability((short) 0);

                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("fix.repaired-item").replace("<item>", ChatColor.RED + Utills.format(item.getType())));

            }
        }
        return false;
    }
}
