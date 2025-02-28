package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PingCommand implements CommandExecutor {

    private Main main;

    public PingCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            int ping = player.getPing();

            if (player.isOnline()) {

                if (args.length == 0) {

                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ping.your-ping").replace("<ping>", Integer.toString(ping)));

                    return true;
                }

                String targetName = args[0];
                Player target = Bukkit.getPlayer(targetName);

                int targetping = target.getPing();

                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ping.target-ping").replace("<target>", targetName).
                        replace("<ping>", Integer.toString(targetping)));

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("ping.invalid-player"));
            }

        }


        return false;
    }
}
