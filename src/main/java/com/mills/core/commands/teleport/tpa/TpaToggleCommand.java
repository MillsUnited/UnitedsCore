package com.mills.core.commands.teleport.tpa;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaToggleCommand implements CommandExecutor {

    private final TpaManager tpaManager;

    public TpaToggleCommand(TpaManager tpaManager) {
        this.tpaManager = tpaManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            tpaManager.toggleTpaRequests(player);
            return true;
        }

        return false;
    }
}
