package com.mills.something.commands;

import com.mills.something.BalanceDataManager;
import com.mills.something.Main;
import com.mills.something.MessagesManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class EconomyCommand implements CommandExecutor {

    private Main main;
    private BalanceDataManager balanceDataManager;
    private MessagesManager messagesManager;

    public EconomyCommand(Main main) {
        this.main = main;
        this.balanceDataManager = main.getBalanceDataManager();
        this.messagesManager = main.getMessagesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // /economy <action> <player> <amount>
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("economy.admin")) {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.no-perm"));
                return true;
            }

            if (args.length < 3) {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-command"));
                return true;
            }

            String action = args[0];
            String targetName = args[1];
            Player target = Bukkit.getPlayer(targetName);

            if (target == null) {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-player"));
                return true;
            }

            double balanceAmount;
            double currentBalance = balanceDataManager.getBalance(player.getUniqueId());

            try {
                balanceAmount = Double.parseDouble(args[2]);
                if (balanceAmount <= 0) {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-amount"));
                    return true;
                }
            } catch (NumberFormatException e) {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-amount"));
                return true;
            }

            double newBalance = 0;

            if (sender == target) {
                if (action.equalsIgnoreCase("deposit")) {
                    newBalance = currentBalance + balanceAmount;
                    balanceDataManager.setBalance(player.getUniqueId(), newBalance);
                    player.sendMessage(main.prefix + messagesManager.getMessage ("economy.admin.deposit")
                            .replace("<amount>", String.valueOf(balanceAmount)));
                } else {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-command"));
                }

                if (action.equalsIgnoreCase("withdraw")) {
                    newBalance = currentBalance - balanceAmount;
                    if (newBalance < 0) {
                        balanceDataManager.setBalance(player.getUniqueId(), 0.0);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.withdraw")
                                .replace("<amount>", "$0.00"));
                        return true;
                    }
                    balanceDataManager.setBalance(player.getUniqueId(), newBalance);
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.withdraw")
                            .replace("<amount>", String.valueOf(balanceAmount)));
                } else {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-command"));
                }
                return true;
            }

            if (action.equalsIgnoreCase("deposit")) {
                newBalance = currentBalance + balanceAmount;
                balanceDataManager.setBalance(player.getUniqueId(), newBalance);
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.deposit")
                        .replace("<player>", String.valueOf(target)).replace("<amount>", String.valueOf(balanceAmount)));
                target.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.deposit-target")
                        .replace("<player>", String.valueOf(player)).replace("<amount>", String.valueOf(balanceAmount)));
            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-command"));
            }

            if (action.equalsIgnoreCase("withdraw")) {
                newBalance = currentBalance - balanceAmount;
                if (newBalance < 0) {
                    balanceDataManager.setBalance(player.getUniqueId(), 0.0);
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.withdraw")
                            .replace("<amount>", "$0.00").replace("<player>", String.valueOf(target)));
                    target.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.withdraw-target")
                            .replace("<amount>", "$0.00").replace("<player>", String.valueOf(player)));
                    return true;
                }
                balanceDataManager.setBalance(player.getUniqueId(), newBalance);
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.withdraw")
                        .replace("<amount>", String.valueOf(balanceAmount)).replace("<player>", String.valueOf(target)));
                target.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.withdraw-target")
                        .replace("<amount>", String.valueOf(balanceAmount)).replace("<player>", String.valueOf(player)));
            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("economy.admin.invalid-command"));
            }
        }
        return false;
    }
}
