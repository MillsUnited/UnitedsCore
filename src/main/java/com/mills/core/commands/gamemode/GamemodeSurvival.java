package com.mills.core.commands.gamemode;

import com.mills.core.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeSurvival implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Main.prefix + "changed gamemode to Survival!");

        }

        return false;
    }
}
