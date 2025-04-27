package com.mills.core.commands.teleport;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetSpawnCommand implements CommandExecutor {

    private String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lSpawn &8» &7");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.spawn.admin")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            Location loc = player.getLocation();
            FileConfiguration config = Main.getInstance().getConfig();
            config.set("Spawn.World-Name", loc.getWorld().getName());
            config.set("Spawn.X", loc.getX());
            config.set("Spawn.Y", loc.getY());
            config.set("Spawn.Z", loc.getZ());
            config.set("Spawn.Yaw", loc.getYaw());
            config.set("Spawn.Pitch", loc.getPitch());
            Main.getInstance().saveConfig();
            player.sendMessage(prefix + "You have set spawn at your location!");
        }
        return false;
    }
}
