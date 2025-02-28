package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class FeedCommand implements CommandExecutor {

    private Main main;

    public FeedCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendMessage(main.prefix + main.getMessagesManager().getMessage("feed.fed"));
            player.setFoodLevel(20);
            player.setSaturation(20);
        }
        return false;
    }
}
