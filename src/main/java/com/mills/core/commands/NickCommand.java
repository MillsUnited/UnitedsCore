package com.mills.core.commands;

import com.mills.core.HexFormatter;
import com.mills.core.Main;
import com.mills.core.NicknameManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NickCommand implements CommandExecutor {

    private final Main main;
    private final NicknameManager nicknameManager;

    public NickCommand(Main main) {
        this.main = main;
        this.nicknameManager = main.getNicknameManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.nick")) {
                player.sendMessage(Main.prefix + "You do not have permission to use this command.");
                return false;
            }

            if (args.length == 0) {
                player.sendMessage(Main.prefix + "/nick <nickname>");
                return true;
            }

            String nick = args[0];
            String parsedNick = HexFormatter.parseHexColor(nick);
            String notboldNick = parsedNick.replace("<bold>", "");

            String colorParsedNick = ChatColor.translateAlternateColorCodes('&', nick);
            String colorNotBoldNick = colorParsedNick.replace("§l", "");

            UUID playerUUID = player.getUniqueId();


            if (!player.hasPermission("server.nick.bold")) {

                nicknameManager.saveNickname(playerUUID, notboldNick);
                player.setDisplayName(ChatColor.translateAlternateColorCodes('&', notboldNick));
                player.sendMessage(Main.prefix + "nicked yourself to " + ChatColor.RED + colorNotBoldNick);

            } else {

                nicknameManager.saveNickname(playerUUID, parsedNick);
                player.setDisplayName(ChatColor.translateAlternateColorCodes('&', parsedNick));
                player.sendMessage(Main.prefix + "nicked yourself to " + ChatColor.RED + colorParsedNick);
            }
        }

        return false;
    }
}
