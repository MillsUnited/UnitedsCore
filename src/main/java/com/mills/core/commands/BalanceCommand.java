package com.mills.core.commands;

import com.mills.core.EconomyManager;
import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BalanceCommand implements CommandExecutor {

    private Main main;
    private EconomyManager economyManager;

    public BalanceCommand(Main main) {
        this.main = main;
        this.economyManager = main.getEconomyManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;


            if (args.length == 0) {
                double playerMessage = economyManager.getBalance(player);

                player.sendMessage(Main.prefix + ChatColor.GREEN +  economyManager.format(playerMessage));
                return true;
            }

            String targetName = args[0];
            Player target = Bukkit.getPlayer(targetName);

            if (target == null || !target.isOnline()) {
                player.sendMessage(Main.prefix + "player is not online!");
                return false;
            }

            if (args.length == 1) {
                double targetMessage = economyManager.getBalance(target);
                player.sendMessage(Main.prefix + ChatColor.GREEN + economyManager.format(targetMessage));
            } else {
                player.sendMessage(Main.prefix + "/bal <player>");
                return true;
            }
        }
        return false;
    }
}
