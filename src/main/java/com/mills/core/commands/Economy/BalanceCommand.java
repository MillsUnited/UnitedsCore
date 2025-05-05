package com.mills.core.commands.Economy;

import com.mills.core.Main;
import com.mills.core.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
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

                player.sendMessage(Main.economyPrefix + ChatColor.GREEN +  economyManager.format(playerMessage));
                return true;
            }

            String targetName = args[0];
            OfflinePlayer target = Bukkit.getPlayer(targetName);

            if (target == null || !target.hasPlayedBefore()) {
                player.sendMessage(Main.economyPrefix + "player has never played before!");
                return false;
            }

            if (args.length == 1) {
                double targetMessage = economyManager.getBalance(target);
                player.sendMessage(Main.economyPrefix + ChatColor.GREEN + Utils.convertBalance(targetMessage));
            } else {
                player.sendMessage(Main.economyPrefix + "/bal <player>");
                return true;
            }
        }
        return false;
    }
}
