package com.mills.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.util.CachedServerIcon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

import static org.bukkit.Bukkit.getLogger;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        e.setJoinMessage(null);

        if (!e.getPlayer().hasPlayedBefore()) {
            FileConfiguration config = Main.getInstance().getConfig();

            String worldName = config.getString("Spawn.World-Name");
            double x = config.getDouble("Spawn.X");
            double y = config.getDouble("Spawn.Y");
            double z = config.getDouble("Spawn.Z");
            float yaw = (float) config.getDouble("Spawn.Yaw");
            float pitch = (float) config.getDouble("Spawn.Pitch");

            World world = Bukkit.getWorld(worldName);

            Location location = new Location(world, x, y, z, yaw, pitch);
            player.teleport(location);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        e.setQuitMessage(null);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        e.setDeathMessage(null);

        Player player = e.getPlayer();
        if (player.hasPermission("server.keepxp")) {
            e.setKeepLevel(true);
            e.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onPlayerHungarLoss(FoodLevelChangeEvent e) {
        HumanEntity player = e.getEntity();

        if (player.hasPermission("server.antihungar")) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPing(ServerListPingEvent e) {
        e.setMaxPlayers(100);
        e.setMotd(ChatColor.translateAlternateColorCodes(
                '&', "                     &4&lInfernalMC\n          &f&l★ &6Skyblock &f&l| &eSeason 1 &f&l★"));

        try (InputStream in = getClass().getResourceAsStream("/logo.png")) {
            if (in != null) {
                BufferedImage image = ImageIO.read(in);
                CachedServerIcon icon = Bukkit.loadServerIcon(image);
                e.setServerIcon(icon);
            } else {
                getLogger().warning("server-icon.png not found in resources!");
            }
        } catch (Exception event) {
            event.printStackTrace();
        }
    }
}