package com.mills.core.commands.teleport.warps;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {

    private final WarpManager warpManager;

    public WarpCommand(Main main) {
        this.warpManager = main.getWarpManager();
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage(warpManager.prefix + "Usage: /warp <name>");
                return true;
            }

            Location warp = warpManager.getWarp(args[0]);
            if (warp == null) {
                player.sendMessage(warpManager.prefix + "Warp '" + args[0] + "' not found.");
                return true;
            }

            player.teleport(warp);
            player.setFlySpeed(0.1F);
            player.setWalkSpeed(0.2F);
            player.sendMessage(warpManager.prefix + "Teleported to warp '" + args[0] + "'!");

        } else {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
        }

        return true;
    }

}
