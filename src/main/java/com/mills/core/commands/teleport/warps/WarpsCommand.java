package com.mills.core.commands.teleport.warps;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.StringJoiner;

public class WarpsCommand implements CommandExecutor {

    private final WarpManager warpManager;

    public WarpsCommand(Main main) {
        this.warpManager = main.getWarpManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        Set<String> warps = warpManager.getAllWarps();
        if (warps.isEmpty()) {
            player.sendMessage(warpManager.prefix + "No warps set.");
            return true;
        }

        String joined = String.join(", ", warps);
        player.sendMessage(warpManager.prefix + joined);
        return true;
    }
}
