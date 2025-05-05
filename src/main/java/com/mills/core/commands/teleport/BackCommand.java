package com.mills.core.commands.teleport;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class BackCommand implements CommandExecutor, Listener {

    private final HashMap<UUID, Location> lastLocations = new HashMap<>();
    private String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lTeleport &8» &7");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (!player.hasPermission("server.back")) {
                player.sendMessage(prefix + "You don't have permission to use this command!");
                return false;
            }

            if (!lastLocations.containsKey(uuid)) {
                player.sendMessage(prefix + "You don't have a previous location to go back to.");
                return true;
            }

            Location backlocation = lastLocations.get(uuid);
            player.teleport(backlocation);
            lastLocations.remove(uuid);
            player.sendMessage(prefix + "Teleported back to your previous location.");

        }

        return false;
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        Player player = e.getPlayer();
        Location from = e.getFrom();
        lastLocations.put(player.getUniqueId(), from);
    }
}
