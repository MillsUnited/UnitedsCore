package com.mills.something.commands;

import com.mills.something.Main;
import com.mills.something.MessagesManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class FlyCommand implements CommandExecutor {

    private Main main;

    public FlyCommand(Main main) {
        this.main = main;
    }

    private boolean fly = false;

    private HashMap<UUID, PermissionAttachment> perms = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.fly")) {

                if (fly) {
                    fly = false;
                    player.sendMessage(main.prefix +  main.getMessagesManager().getMessage("fly.disable"));
                    player.setAllowFlight(false);
                } else {
                    fly = true;
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("fly.enable"));
                    player.setAllowFlight(true);
                }

            } else {
                player.sendMessage(main.prefix + main.getMessagesManager().getMessage("fly.no-perm"));
            }
        }
        return false;
    }
}
