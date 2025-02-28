package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PTimeCommand implements CommandExecutor {

    private Main main;

    public PTimeCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.ptime"))

                if (args.length == 1) {

                    if (args[0].equalsIgnoreCase("day")) {

                        player.setPlayerTime(1000, true);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ptime.day"));

                    } else if (args[0].equalsIgnoreCase("midnight")) {
                        player.setPlayerTime(18000, true);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ptime.midnight"));

                    } else if (args[0].equalsIgnoreCase("night")) {
                        player.setPlayerTime(13000, true);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ptime.night"));

                    } else if (args[0].equalsIgnoreCase("noon")) {
                        player.setPlayerTime(6000, true);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ptime.noon"));

                    } else if (args[0].equalsIgnoreCase("reset")) {
                        player.resetPlayerTime();
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ptime.reset"));
                    }

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ptime.invalid-command"));
            }

        }
        return false;
    }
}
