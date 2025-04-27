package com.mills.core.commands.teleport.warps;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelWarpCommand implements CommandExecutor {

    private final WarpManager warpManager;

    public DelWarpCommand(Main main) {
        this.warpManager = main.getWarpManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (!sender.hasPermission(warpManager.admin)) {
            sender.sendMessage(Main.prefix + "You don't have permission to use this command!");
            return true;
        }

        if (args.length != 1) {
            player.sendMessage(warpManager.prefix + "Usage: /delwarp <name>");
            return true;
        }

        if (warpManager.getWarp(args[0]) == null) {
            player.sendMessage(warpManager.prefix + "Warp '" + args[0] + "' does not exist.");
            return true;
        }

        warpManager.removeWarp(args[0]);
        player.sendMessage(warpManager.prefix + "Warp '" + args[0] + "' removed.");
        return true;
    }
}
