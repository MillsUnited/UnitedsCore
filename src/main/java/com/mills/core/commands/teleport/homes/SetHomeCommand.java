package com.mills.core.commands.teleport.homes;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SetHomeCommand implements CommandExecutor {

    private Main main;
    private HomesManager homesManager;

    public SetHomeCommand(Main main) {
        this.main = main;
        this.homesManager = main.getHomesManager();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID uuid = player.getUniqueId();

            int currentHomes = homesManager.getHomeamount(uuid);
            int homeLimit = getHomeLimit(player);

            if (currentHomes >= homeLimit) {
                player.sendMessage(homesManager.prefix + "You have reached your home limit (" + homeLimit + ")!");
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(homesManager.prefix + "Invalid name, provide a name!");
                return true;
            }

            String home = args[0];
            int limit = 10;
            if (home.length() >= limit) {
                player.sendMessage(homesManager.prefix + "character limit reached, make your home >= 10 character limit!");
                return true;
            }

            if (homesManager.doesHomeExist(uuid, home)) {
                player.sendMessage(homesManager.prefix + "you already have a home called " + home);
            }

            double x = player.getLocation().getX();
            double y = player.getLocation().getY();
            double z = player.getLocation().getZ();
            float yaw = player.getLocation().getYaw();
            float pitch = player.getLocation().getPitch();
            World world = player.getWorld();

            homesManager.saveHome(uuid, world.getName(), home, x, y, z, yaw, pitch);
            player.sendMessage(homesManager.prefix + "set home " + home + "!");
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (online.hasPermission(homesManager.admin)) {
                    if (player.equals(online)) {
                        continue;
                    }
                    online.sendMessage(homesManager.prefix + player.getName() + " created home '" +
                            home + "' at " + "(" + (int) x + ", " + (int) y + ", " + (int) x + ") at world '" + world.getName() + "'");
                }
            }

        }
        return false;
    }

    private int getHomeLimit(Player player) {
        if (player.hasPermission("server.homes.amount.infernal") || player.hasPermission(homesManager.admin)) {
            return Integer.MAX_VALUE;
        } else if (player.hasPermission("server.homes.amount.blaze")) {
            return 15;
        } else if (player.hasPermission("server.homes.amount.ember")) {
            return 7;
        }
        return 3;
    }
}
