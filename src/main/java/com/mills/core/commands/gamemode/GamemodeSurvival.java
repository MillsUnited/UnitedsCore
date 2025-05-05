package com.mills.core.commands.gamemode;

import com.mills.core.Main;
import org.bukkit.Bukkit;
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

            if (!player.hasPermission("server.gamemode")) {
                player.sendMessage(Main.prefix + "You do not have permission to use this command.");
                return false;
            }

            if (args.length == 0) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Main.prefix + "You changed your gamemode to Survival!");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[0]);

            if (target == null) {
                player.sendMessage(Main.prefix + "Player not found!");
                return true;
            }

            if (target.equals(player)) {
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(Main.prefix + "You changed your gamemode to Survival!");
                return true;
            }

            target.setGameMode(GameMode.SURVIVAL);
            target.sendMessage(Main.prefix + "Your gamemode has been changed to Survival by " + player.getName() + "!");
            player.sendMessage(Main.prefix + "You changed " + target.getName() + "'s gamemode to Survival!");

        }

        return false;
    }
}
