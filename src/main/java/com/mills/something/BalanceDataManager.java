package com.mills.something;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.UUID;

public class BalanceDataManager {

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

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(file);
    }
}
