package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PWeatherCommand implements CommandExecutor {

    private Main main;

    public PWeatherCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (args.length == 1) {

                if (args[0].equalsIgnoreCase("clear")) {

                    player.setPlayerWeather(WeatherType.CLEAR);
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("pweather.clear"));

                } else if (args[0].equalsIgnoreCase("downfall")) {
                    player.setPlayerWeather(WeatherType.DOWNFALL);
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("pweather.downfall"));

                } else if (args[0].equalsIgnoreCase("reset")) {
                    player.resetPlayerWeather();
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("pweather.reset"));
                }

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("pweather.invalid-command"));
            }

        }
        return false;
    }
}
