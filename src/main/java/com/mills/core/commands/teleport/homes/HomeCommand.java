package com.mills.core.commands.teleport.homes;

import com.mills.core.Main;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HomeCommand implements CommandExecutor {

    private Main main;
    private HomesManager homesManager;

    public HomeCommand(Main main) {
        this.main = main;
        this.homesManager = this.main.getHomesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (args.length == 0) {
                player.sendMessage(homesManager.prefix + "Usage: /home <homeName>");
                return true;
            }

            String home = args[0];

            if (!homesManager.doesHomeExist(uuid, home)) {
                player.sendMessage(homesManager.prefix + "Home does not exist!");
                return true;
            }

            double x = homesManager.getHomeX(uuid, home);
            double y = homesManager.getHomeY(uuid, home);
            double z = homesManager.getHomeZ(uuid, home);
            float yaw = homesManager.getHomeYaw(uuid, home);
            float pitch = homesManager.getHomePitch(uuid, home);
            World world = homesManager.getHomeWorld(uuid, home);

            if (world == null) {
                player.sendMessage(homesManager.prefix + "Could not find the world for home " + home);
                return true;
            }

            Location loc = new Location(world, x, y, z, yaw, pitch);
            player.teleport(loc);
            player.sendMessage(homesManager.prefix + "Teleported to home " + home);
        }
        return false;
    }
}
