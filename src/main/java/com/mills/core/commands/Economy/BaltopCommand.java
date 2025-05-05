package com.mills.core.commands.Economy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BaltopCommand implements CommandExecutor {

    private BaltopGUI baltopGUI;

    public BaltopCommand(BaltopGUI baltopGUI) {
        this.baltopGUI = baltopGUI;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            baltopGUI.baltop(player);
        }
        return false;
    }
}
