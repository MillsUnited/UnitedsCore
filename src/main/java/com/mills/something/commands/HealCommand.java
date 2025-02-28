package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HealCommand implements CommandExecutor {

    private Main main;

    public HealCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("adminperm")) {

                if (args.length == 1) {

                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[0]);

                    if (offlinePlayer.isOnline()) {
                        offlinePlayer.getPlayer().setHealth(20);
                        player.sendMessage(main.prefix + main.getMessagesManager().getMessage("healed-by-player").replace("<player>", sender.getName()));

                    }
                    return true;
                }
            }
            if (player.hasPermission("server.heal")) {

                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("heal.healed"));
                player.setHealth(20);
            }
        }
        return false;
    }
}
