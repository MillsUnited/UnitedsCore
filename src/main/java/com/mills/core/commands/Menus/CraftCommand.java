package com.mills.core.commands.Menus;

import com.mills.core.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CraftCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.workbench")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            player.openWorkbench(null, true);

        }


        return false;
    }
}
