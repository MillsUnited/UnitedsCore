package com.mills.something.commands;

import com.mills.something.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class NearCommand implements CommandExecutor {

    private Main main;

    public NearCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            StringBuilder builder = new StringBuilder();
            builder.append(main.prefix);

            for (Entity near : player.getWorld().getNearbyEntities(player.getLocation(), 50, 50, 50)) {

                if (near instanceof Player nearPlayer) {

                    builder.append(nearPlayer.getName() + ", ");

                }

            }

            player.sendMessage(builder.toString());
        }

        return false;
    }
}
