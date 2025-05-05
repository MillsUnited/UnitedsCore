package com.mills.core.commands.Economy;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BalanceDataManager implements Listener {

    private final File file;
    private FileConfiguration config;

    public BalanceDataManager(File dataFolder) {
        file = new File(dataFolder, "balances.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public Double getBalance(UUID uuid) {
        return config.getDouble(uuid.toString());
    }

    public void setBalance(UUID uuid, Double balance) {
        config.set(uuid.toString(), balance);
        saveConfig();
    }

    public boolean contains(UUID uuid) {
        return config.contains(uuid.toString());
    }

    public Set<String> getAllUUIDs() {
        return config.getKeys(false);
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!contains(uuid)) {
            setBalance(uuid, 0.0);
        }
    }
}
