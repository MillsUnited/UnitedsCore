package com.mills.core.commands;

import com.mills.core.Main;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

public class SpeedCommand implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (player.hasPermission("server.speed"))

                if (args.length == 1) {

                    if (args[0].equalsIgnoreCase("1")) {

                        player.setFlySpeed(0.1F);
                        player.setWalkSpeed(0.2F);
                        player.sendMessage(Main.prefix + "Changed speed to 1!");

                    } else if (args[0].equalsIgnoreCase("2")) {

                        player.setFlySpeed(0.2F);
                        player.setWalkSpeed(0.4F);
                        player.sendMessage(Main.prefix + "Changed speed to 2!");

                    } else if (args[0].equalsIgnoreCase("3")) {

                        player.setFlySpeed(0.3F);
                        player.setWalkSpeed(0.6F);
                        player.sendMessage(Main.prefix + "Changed speed to 3!");

                    } else if (args[0].equalsIgnoreCase("4")) {

                        player.setFlySpeed(0.4F);
                        player.setWalkSpeed(0.8F);
                        player.sendMessage(Main.prefix + "Changed speed to 4!");

                    } else if (args[0].equalsIgnoreCase("5")) {

                        player.setFlySpeed(0.5F);
                        player.setWalkSpeed(1.0F);
                        player.sendMessage(Main.prefix + "Changed speed to 5!");

                    } else if (args[0].equalsIgnoreCase("reset")) {

                        player.setFlySpeed(0.1F);
                        player.setWalkSpeed(0.2F);
                        player.sendMessage(Main.prefix + "reset speed");
                    } else {
                        player.sendMessage(Main.prefix + "/speed <speed>");
                    }

                } else {
                    player.sendMessage(Main.prefix + "/speed <speed>");
                }
        }
        return false;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        World toWorld = event.getTo().getWorld();

        if (toWorld != null && toWorld.getName().equalsIgnoreCase("pvp")) {
            float walkSpeed = player.getWalkSpeed();
            float flySpeed = player.getFlySpeed();
            if (walkSpeed > 0.2F) {
                player.setWalkSpeed(0.2F);
            }
            if (flySpeed > 0.1F) {
                player.setWalkSpeed(0.1F);
            }
        }
    }
}
