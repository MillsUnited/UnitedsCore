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

public class EconomyCommand implements CommandExecutor {

    private Main main;
    private BalanceDataManager balanceDataManager;
    private EconomyManager economyManager;

    public EconomyCommand(Main main) {
        this.main = main;
        this.balanceDataManager = main.getBalanceDataManager();
        this.economyManager = main.getEconomyManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // /economy <action> <player> [<amount>]
        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("server.economy.admin")) {
            player.sendMessage(Main.economyPrefix + "You do not have permission to use this command.");
            return true;
        }

        if (args.length < 2) {
            player.sendMessage(Main.economyPrefix + "/economy <take/give/set/reset> <player> [<amount>]");
            return true;
        }

        String action = args[0].toLowerCase();
        String targetName = args[1];
        OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

        if (!target.hasPlayedBefore()) {
            player.sendMessage(Main.economyPrefix + "That player has never joined the server!");
            return true;
        }

        if (action.equals("reset")) {
            balanceDataManager.setBalance(target.getUniqueId(), 0.0);
            if (!player.equals(target)) {
                player.sendMessage(Main.economyPrefix + "You reset the balance of " + ChatColor.GREEN + target.getName() + ChatColor.GRAY + ".");
            }
            if (target.isOnline()) {
                ((Player) target).sendMessage(Main.economyPrefix + "Your balance has been reset.");
            }
            return true;
        }

        if (args.length < 3) {
            player.sendMessage(Main.economyPrefix + "/economy <take/give/set/reset> <player> [<amount>]");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            player.sendMessage(Main.economyPrefix + "Please enter a valid amount greater than 0!");
            return true;
        }

        double targetBalance = balanceDataManager.getBalance(target.getUniqueId());

        switch (action) {
            case "give" -> {
                balanceDataManager.setBalance(target.getUniqueId(), targetBalance + amount);
                if (!player.equals(target)) {
                    player.sendMessage(Main.economyPrefix + "Deposited " + Utils.convertBalance(amount) +
                            ChatColor.GRAY + " into " + ChatColor.GREEN + target.getName() + ChatColor.GRAY + "'s balance.");
                }
                if (target.isOnline()) {
                    ((Player) target).sendMessage(Main.economyPrefix + "You received " +
                            Utils.convertBalance(amount) + ChatColor.GRAY + ".");
                }
            }
            case "take" -> {
                double newBalance = Math.max(0, targetBalance - amount);
                balanceDataManager.setBalance(target.getUniqueId(), newBalance);
                String formattedAmount = String.valueOf(targetBalance < amount ? targetBalance : amount);
                if (!player.equals(target)) {
                    player.sendMessage(Main.economyPrefix + "Withdrew " +
                            Utils.convertBalance(Double.parseDouble(formattedAmount)) + ChatColor.GRAY + " from " +
                            ChatColor.GREEN + target.getName() + ChatColor.GRAY + "'s balance.");
                }
                if (target.isOnline()) {
                    ((Player) target).sendMessage(Main.economyPrefix +
                            Utils.convertBalance(Double.parseDouble(formattedAmount)) + ChatColor.GRAY + " was withdrawn from your balance.");
                }
            }
            case "set" -> {
                double newBalance = Math.max(0, amount);
                balanceDataManager.setBalance(target.getUniqueId(), newBalance);
                if (!player.equals(target)) {
                    player.sendMessage(Main.economyPrefix + "You set " +
                            ChatColor.GREEN + targetName + ChatColor.GRAY + "'s balance to " +
                            Utils.convertBalance(newBalance) + ChatColor.GRAY + ".");
                }
                if (target.isOnline()) {
                    ((Player) target).sendMessage(Main.economyPrefix + "Your balance has been set to " +
                            Utils.convertBalance(newBalance) + ChatColor.GRAY + ".");
                }
            }
            default -> {
                player.sendMessage(Main.economyPrefix + "/economy <take/give/set/reset> <player> [<amount>]");
                return true;
            }
        }
        return false;
    }
}
