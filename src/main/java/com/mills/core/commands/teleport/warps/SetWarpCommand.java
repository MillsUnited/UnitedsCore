package com.mills.core.commands.teleport.warps;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetWarpCommand implements CommandExecutor {

    private final WarpManager warpManager;

    public SetWarpCommand(Main main) {
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
            player.sendMessage(warpManager.prefix + "Usage: /setwarp <name>");
            return true;
        }

        warpManager.saveWarp(args[0], player.getLocation());
        player.sendMessage(warpManager.prefix + "Set warp '" + args[0] + "' at your location!");
        return true;
    }
}
