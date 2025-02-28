package com.mills.something.commands;

import com.mills.something.Main;
import com.mills.something.NicknameManager;
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

            if (player.hasPermission("server.nick")) {

                if (args.length == 0) {

                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("nickname.invalid-command"));

                    return true;
                }


                String nick = args[0];
                String parsedNick = parseHexColors(nick);

                String notboldnick = parsedNick.replace("&l", "");

                UUID playerUUID = player.getUniqueId();

                if (!player.hasPermission("nick.bold")) {

                    if (parsedNick.contains("&l")) {

                        nicknameManager.saveNickname(playerUUID, notboldnick);

                        player.setDisplayName(ChatColor.translateAlternateColorCodes('&', notboldnick));

                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("nickname.nicknamed").
                                replace("<nick>", ChatColor.RED + ChatColor.translateAlternateColorCodes('&', notboldnick)));

                    }
                    return true;
                }

                nicknameManager.saveNickname(playerUUID, parsedNick);

                player.setDisplayName(ChatColor.translateAlternateColorCodes('&', parsedNick));

                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("nickname.nicknamed").
                        replace("<nick>", ChatColor.RED + ChatColor.translateAlternateColorCodes('&', parsedNick)));

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("nickname.no-perm"));
            }

        }

        return false;
    }

    private String parseHexColors(String input) {
        Pattern pattern = Pattern.compile("&#([a-fA-F0-9]{6})");
        Matcher matcher = pattern.matcher(input);

        StringBuffer buffer = new StringBuffer();

        while (matcher.find()) {
            String hex = matcher.group(1);
            String colorCode = net.md_5.bungee.api.ChatColor.of("#" + hex).toString();
            matcher.appendReplacement(buffer, colorCode);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }
}
