package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PWeatherCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.pweather")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("clear")) {

                    player.setPlayerWeather(WeatherType.CLEAR);
                    player.sendMessage(Main.prefix + "Changed time to Clear!");

                } else if (args[0].equalsIgnoreCase("downfall")) {
                    player.setPlayerWeather(WeatherType.DOWNFALL);
                    player.sendMessage(Main.prefix + "Changed weather to Downfall!");

                } else if (args[0].equalsIgnoreCase("reset")) {
                    player.resetPlayerWeather();
                    player.sendMessage(Main.prefix + "reset player weather!");
                }

            } else {
                player.sendMessage(Main.prefix + "/pweather <weather>");
            }

        }
        return false;
    }
}
