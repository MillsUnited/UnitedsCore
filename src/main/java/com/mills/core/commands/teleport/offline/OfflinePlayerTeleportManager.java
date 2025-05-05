package com.mills.core.commands.teleport.offline;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class OfflinePlayerTeleportManager implements Listener {

    private final File file;
    private FileConfiguration config;

    public OfflinePlayerTeleportManager(File dataFolder) {
        file = new File(dataFolder, "offlinecache.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void savePlayerLocation(Player player) {
        UUID uuid = player.getUniqueId();
        Location loc = player.getLocation();

        config.set(uuid + ".world", loc.getWorld().getName());
        config.set(uuid + ".x", loc.getX());
        config.set(uuid + ".y", loc.getY());
        config.set(uuid + ".z", loc.getZ());
        config.set(uuid + ".yaw", loc.getYaw());
        config.set(uuid + ".pitch", loc.getPitch());

        saveConfig();
    }

    public Location getPlayerLocation(UUID uuid) {
        String worldName = config.getString(uuid + ".world");
        if (worldName == null) return null;

        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;

        double x = config.getDouble(uuid + ".x");
        double y = config.getDouble(uuid + ".y");
        double z = config.getDouble(uuid + ".z");
        float yaw = (float) config.getDouble(uuid + ".yaw");
        float pitch = (float) config.getDouble(uuid + ".pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    public void removePlayerData(UUID uuid) {
        config.set(uuid.toString(), null);
        saveConfig();
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        savePlayerLocation(e.getPlayer());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        savePlayerLocation(e.getPlayer());
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e) {
        savePlayerLocation(e.getPlayer());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        removePlayerData(uuid);
        reloadConfig();
    }
}
