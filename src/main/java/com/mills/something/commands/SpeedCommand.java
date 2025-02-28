package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpeedCommand implements CommandExecutor {

    private Main main;

    public SpeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.speed"))

                if (args.length == 1) {

                    if (args[0].equalsIgnoreCase("1")) {

                        player.setFlySpeed(0.1F);
                        player.setWalkSpeed(0.2F);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("speed.1"));

                    } else if (args[0].equalsIgnoreCase("2")) {

                        player.setFlySpeed(0.2F);
                        player.setWalkSpeed(0.4F);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("speed.2"));

                    } else if (args[0].equalsIgnoreCase("3")) {

                        player.setFlySpeed(0.3F);
                        player.setWalkSpeed(0.6F);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("speed.3"));

                    } else if (args[0].equalsIgnoreCase("4")) {

                        player.setFlySpeed(0.4F);
                        player.setWalkSpeed(0.8F);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("speed.4"));

                    } else if (args[0].equalsIgnoreCase("5")) {

                        player.setFlySpeed(0.5F);
                        player.setWalkSpeed(1.0F);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("speed.5"));

                    } else if (args[0].equalsIgnoreCase("reset")) {

                        player.setFlySpeed(0.1F);
                        player.setWalkSpeed(0.2F);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("speed.reset"));
                    }

                } else {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("speed.invalid-command"));
                }



        }

        return false;
    }
}
