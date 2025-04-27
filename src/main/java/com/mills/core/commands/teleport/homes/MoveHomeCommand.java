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

public class MoveHomeCommand implements CommandExecutor {

    private Main main;
    private HomesManager homesManager;

    public MoveHomeCommand(Main main) {
        this.main = main;
        this.homesManager = this.main.getHomesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            if (args.length == 0) {
                player.sendMessage(homesManager.prefix + "Usage: /movehome <homeName>");
                return false;
            }

            String home = args[0];

            if (!homesManager.doesHomeExist(uuid, home)) {
                player.sendMessage(homesManager.prefix + "Home does not exist!");
                return false;
            }

            Location loc = player.getLocation();

            double x = loc.getX();
            double y = loc.getY();
            double z = loc.getZ();
            float yaw = loc.getYaw();
            float pitch = loc.getPitch();
            World world = loc.getWorld();

            if (world == null) {
                player.sendMessage(homesManager.prefix + "Could not find the world for home " + home);
                return true;
            }

            homesManager.updateHomeLocation(uuid, home, world.getName(), x, y, z, yaw, pitch);
        }
        return false;
    }
}
