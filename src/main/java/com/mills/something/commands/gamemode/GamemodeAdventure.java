package com.mills.something.commands.gamemode;

import com.mills.something.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeAdventure implements CommandExecutor {

    private Main main;

    public GamemodeAdventure(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage(main.prefix + main.getMessagesManager().getMessage("gamemode.adventure-message"));

        }


        return false;
    }
}
