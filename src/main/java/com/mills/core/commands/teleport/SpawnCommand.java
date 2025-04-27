package com.mills.core.commands.teleport;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnCommand implements CommandExecutor {

    private String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lSpawn &8» &7");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            FileConfiguration config = Main.getInstance().getConfig();

            String worldName = config.getString("Spawn.World-Name");
            double x = config.getDouble("Spawn.X");
            double y = config.getDouble("Spawn.Y");
            double z = config.getDouble("Spawn.Z");
            float yaw = (float) config.getDouble("Spawn.Yaw");
            float pitch = (float) config.getDouble("Spawn.Pitch");

            World world = Bukkit.getWorld(worldName);

            if (world == null) {
                player.sendMessage(prefix + "The world " + worldName + " does not exist!");
                return true;
            }

            Location location = new Location(world, x, y, z, yaw, pitch);
            player.teleport(location);
            player.sendMessage(prefix + "Teleported to spawn!");
            return true;
        }

        sender.sendMessage(prefix + "Only players can use this command.");
        return true;
    }
}
