package com.mills.something.commands;

import com.mills.something.Main;
import com.mills.something.Utills;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SeenCommand implements CommandExecutor {

    private Main main;

    public SeenCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;


            if (!player.isOnline()) {

                long seen = player.getLastPlayed();

                long currentTime = System.currentTimeMillis();
                long timeDifference = currentTime - seen;
                String timeAgo = Utills.formatTimeAgo(timeDifference);

                if(args.length == 0) {
                    player.sendMessage(main.prefix + timeAgo);

                    return true;
                }

                String targetName = args[0];
                OfflinePlayer target = Bukkit.getOfflinePlayer(targetName);

                if (!target.hasPlayedBefore()) {
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("seen.invalid-player").replace("<player>", targetName));

                    return true;
                }

                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("seen.seen-player").replace("<player>", targetName).replace("<time>", timeAgo));

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("seen.player-online"));
            }
        }

        return false;
    }
}
