package com.mills.core.commands.teleport.offline;

import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OfflinePlayerTeleportCommand implements CommandExecutor {

    private Main main;
    private OfflinePlayerTeleportManager offlinePlayerTeleportManager;

    public OfflinePlayerTeleportCommand(Main main) {
        this.main = main;
        this.offlinePlayerTeleportManager = main.getOfflinePlayerTeleportManager();
    }

    private String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lTeleport &8» &7");

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;

            if (!player.hasPermission("server.teleport")) {
                player.sendMessage(Main.prefix + "You don't have permission to use this command!");
                return false;
            }

            if (args.length == 0) {
                player.sendMessage(player + "Ussage: /offlinetp <player>");
            }

            OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
            UUID targetUUID = target.getUniqueId();

            Location targetLoc = offlinePlayerTeleportManager.getPlayerLocation(targetUUID);

            if (targetLoc == null) {
                player.sendMessage(prefix + "That player has no saved location.");
                return true;
            }

            player.teleport(targetLoc);
            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', "Teleported to &e" + target.getName() + "§7's last saved location!"));
        }
        return false;
    }
}
