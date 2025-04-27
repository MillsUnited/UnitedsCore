package com.mills.core.commands.teleport.tpa;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaCommand implements CommandExecutor {

    private final TpaManager tpaManager;

    public TpaCommand(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length != 1) {
                player.sendMessage(tpaManager.prefix + "Usage: /tpa <player>");
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(tpaManager.prefix + "Player not found or not online.");
                return false;
            }

            if (player.equals(target)) {
                player.sendMessage(tpaManager.prefix + "You cannot send a TPA request to yourself.");
                return false;
            }

            if (!tpaManager.canSendRequest(player)) {
                return false;
            }

            return tpaManager.sendTpaRequest(player, target);
        }

        return false;
    }

}
