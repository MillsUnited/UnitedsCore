package com.mills.core.commands.gamemode;

import com.mills.core.Main;
import com.mills.core.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.gamemode")) {
                player.sendMessage(Main.prefix + "You do not have permission to use this command.");
                return false;
            }

            if (args.length == 0) {
                player.sendMessage(Main.prefix + "/gamemode <creative|survival|spectator|adventure>");
                return false;
            }

            GameMode gamemode;
            switch (args[0].toLowerCase()) {
                case "survival":
                    gamemode = GameMode.SURVIVAL;
                    break;
                case "creative":
                    gamemode = GameMode.CREATIVE;
                    break;
                case "spectator":
                    gamemode = GameMode.SPECTATOR;
                    break;
                case "adventure":
                    gamemode = GameMode.ADVENTURE;
                    break;
                default:
                    player.sendMessage(Main.prefix + "/gamemode <creative|survival|spectator|adventure> [player]");
                    return true;
            }

            if (args.length == 1) {
                player.setGameMode(gamemode);
                player.sendMessage(Main.prefix + "Your gamemode has been changed to " + Utils.format(gamemode.name()) + "!");
                return true;
            }

            Player target = Bukkit.getPlayerExact(args[1]);

            if (target == null) {
                player.sendMessage(Main.prefix + "Player not found!");
                return true;
            }

            if (target.equals(player)) {
                player.setGameMode(gamemode);
                player.sendMessage(Main.prefix + "Your gamemode has been changed to " + Utils.format(gamemode.name()) + "!");
                return true;
            }

            target.setGameMode(gamemode);
            target.sendMessage(Main.prefix + "Your gamemode has been changed to " + gamemode.name().toLowerCase() + " by " + player.getName() + "!");
            player.sendMessage(Main.prefix + "You changed " + target.getName() + "'s gamemode to " + gamemode.name().toLowerCase() + "!");
        }

        return false;
    }
}
