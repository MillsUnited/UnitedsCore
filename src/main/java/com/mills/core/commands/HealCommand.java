package com.mills.core.commands;

import com.mills.core.CooldownManager;
import com.mills.core.Main;
import com.mills.core.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

    private long cooldown = 300000;
    private String key = "heal";

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.heal")) {

                if (!player.hasPermission("server.cooldown.bypass")) {
                    if (CooldownManager.isOnCooldown(player, key, cooldown)) {
                        long timeLeft = CooldownManager.getTimeLeft(player, key, cooldown);
                        player.sendMessage(Main.prefix + ChatColor.RED + "You're on cooldown for " + Utils.formatTime(timeLeft));
                        return false;
                    }
                }

                player.sendMessage(Main.prefix + "You have been healed!");
                player.setHealth(20);
                CooldownManager.setCooldown(player, key);

            } else {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
            }
        }
        return false;
    }
}
