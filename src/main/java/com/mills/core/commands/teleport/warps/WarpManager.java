package com.mills.core.commands.teleport.warps;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Set;

public class WarpManager {

    private final File file;
    private FileConfiguration config;

    public String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lWarp &8» &7");
    public String admin = "server.warp.admin";

    public WarpManager(File dataFolder) {
        file = new File(dataFolder, "warps.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveWarp(String name, Location location) {
        config.set(name + ".world", location.getWorld().getName());
        config.set(name + ".x", location.getX());
        config.set(name + ".y", location.getY());
        config.set(name + ".z", location.getZ());
        config.set(name + ".yaw", location.getYaw());
        config.set(name + ".pitch", location.getPitch());
        saveConfig();
    }

    public Location getWarp(String name) {
        if (!config.contains(name)) return null;

        String worldName = config.getString(name + ".world");
        World world = Bukkit.getWorld(worldName);
        if (world == null) return null;

        double x = config.getDouble(name + ".x");
        double y = config.getDouble(name + ".y");
        double z = config.getDouble(name + ".z");
        float yaw = (float) config.getDouble(name + ".yaw");
        float pitch = (float) config.getDouble(name + ".pitch");

        return new Location(world, x, y, z, yaw, pitch);
    }

    public Set<String> getAllWarps() {
        return config.getKeys(false);
    }

    public void removeWarp(String name) {
        config.set(name, null);
        saveConfig();
    }

    private void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

}
