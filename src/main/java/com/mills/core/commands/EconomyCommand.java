package com.mills.core.commands;

import com.mills.core.BalanceDataManager;
import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyCommand implements CommandExecutor {

    private Main main;
    private BalanceDataManager balanceDataManager;

    public EconomyCommand(Main main) {
        this.main = main;
        this.balanceDataManager = main.getBalanceDataManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // /economy <action> <player> <amount>
        if (!(sender instanceof Player player)) return false;

        if (!player.hasPermission("server.economy.admin")) {
            player.sendMessage(Main.prefix + "You don't have permission.");
            return true;
        }

        if (args.length < 3) {
            player.sendMessage(Main.prefix + "/economy <deposit|withdraw> <player> <amount>");
            return true;
        }

        String action = args[0].toLowerCase();
        String targetName = args[1];
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            player.sendMessage(Main.prefix + "Player is not online!");
            return true;
        }

        double amount;
        try {
            amount = Double.parseDouble(args[2]);
            if (amount <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            player.sendMessage(Main.prefix + "Please enter a valid amount greater than 0!");
            return true;
        }

        double targetBalance = balanceDataManager.getBalance(target.getUniqueId());

        switch (action) {
            case "deposit" -> {
                balanceDataManager.setBalance(target.getUniqueId(), targetBalance + amount);
                player.sendMessage(Main.prefix + "Deposited $" + amount + " into " + target.getName() + "'s balance.");
                target.sendMessage(Main.prefix + "You received $" + amount + " from " + player.getName() + ".");
            }
            case "withdraw" -> {
                double newBalance = Math.max(0, targetBalance - amount);
                balanceDataManager.setBalance(target.getUniqueId(), newBalance);
                String formattedAmount = String.valueOf(targetBalance < amount ? targetBalance : amount);
                player.sendMessage(Main.prefix + "Withdrew $" + formattedAmount + " from " + target.getName() + "'s balance.");
                target.sendMessage(Main.prefix + "$" + formattedAmount + " was withdrawn from your balance by " + player.getName() + ".");
            }
            default -> {
                player.sendMessage(Main.prefix + "/economy <withdraw/deposit> <player> <amount>");
                return true;
            }
        }
        return false;
    }
}
