package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FlyCommand implements CommandExecutor {

    private boolean fly = false;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.fly")) {

                if (fly) {
                    fly = false;
                    player.sendMessage(Main.prefix +  "Fly disabled");
                    player.setAllowFlight(false);
                } else {
                    fly = true;
                    player.sendMessage(Main.prefix + "Fly enabled");
                    player.setAllowFlight(true);
                }

            } else {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
            }
        }
        return false;
    }
}
