package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VanishCommand implements CommandExecutor {

    private Main main;

    public VanishCommand(Main main) {
        this.main = main;
    }

    public static List<UUID> vanished = new ArrayList<>();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("staffperm")) {

                if (vanished.contains(player.getUniqueId())) {

                    vanished.remove(player.getUniqueId());
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.showPlayer(player);
                    }
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("vanish.disable"));


                } else {

                    vanished.add(player.getUniqueId());
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        target.hidePlayer(player);
                    }
                    player.sendMessage(main.prefix + main.getMessagesManager().getMessage("vanish.enable"));

                }
            }
        }
        return false;
    }
}

