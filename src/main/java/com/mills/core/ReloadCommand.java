package com.mills.core;

import com.mills.core.commands.Economy.BalanceDataManager;
import com.mills.core.commands.teleport.warps.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ReloadCommand implements CommandExecutor {

    private Main main;
    private final NicknameManager nicknameManager;
    private final ChatcolorManager chatcolorManager;
    private final BalanceDataManager balanceDataManager;
    private final WarpManager warpManager;

    public ReloadCommand(Main main) {
        this.main = main;
        this.nicknameManager = main.getNicknameManager();
        this.chatcolorManager = main.getChatcolorManager();
        this.balanceDataManager = main.getBalanceDataManager();
        this.warpManager = main.getWarpManager();

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.config.reload")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            main.reloadConfig();
            player.sendMessage(Main.prefix + "reloaded config");

            warpManager.reloadConfig();

            nicknameManager.reloadConfig();
            for (Player onlinePlayer : main.getServer().getOnlinePlayers()) {
                applyNickname(onlinePlayer);
            }
            chatcolorManager.reloadConfig();
            for (Player onlinePlayer : main.getServer().getOnlinePlayers()) {
                applyChatcolor(onlinePlayer);
            }
            balanceDataManager.reloadConfig();
            for (Player onlinePlayer : main.getServer().getOnlinePlayers()) {
                applyBalance(onlinePlayer);
            }

        } else {
            main.reloadConfig();
            sender.sendMessage("reloaded config");

            nicknameManager.reloadConfig();
            for (Player onlinePlayer : main.getServer().getOnlinePlayers()) {
                applyNickname(onlinePlayer);
            }
            chatcolorManager.reloadConfig();
            for (Player onlinePlayer : main.getServer().getOnlinePlayers()) {
                applyChatcolor(onlinePlayer);
            }
            balanceDataManager.reloadConfig();
            for (Player onlinePlayer : main.getServer().getOnlinePlayers()) {
                applyBalance(onlinePlayer);
            }
        }

        return false;
    }

    private void applyNickname(Player player) {
        String nickname = nicknameManager.getNickname(player.getUniqueId());
        if (nickname != null) {
            player.setDisplayName(nickname);
        }
    }

    private void applyChatcolor(Player player) {
        String chatcolor = chatcolorManager.getChatcolor(player.getUniqueId());
        if (chatcolor != null) {
            chatcolorManager.saveChatcolor(player.getUniqueId(), chatcolor);
        }
    }

    private void applyBalance(Player player) {
        Double balance = balanceDataManager.getBalance(player.getUniqueId());
        if (balance != null) {
            balanceDataManager.setBalance(player.getUniqueId(), balance);
        }
    }
}
