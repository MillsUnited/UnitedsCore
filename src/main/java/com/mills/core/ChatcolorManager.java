package com.mills.core;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ChatcolorManager implements Listener {

    private File file;
    private FileConfiguration config;

    public ChatcolorManager(File dataFolder) {
        file = new File(dataFolder, "chatcolors.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveChatcolor(UUID uuid, String chatcolor) {
        config.set(uuid.toString(), chatcolor);
        saveConfig();
    }

    public String getChatcolor(UUID uuid) {
        return config.getString(uuid.toString());
    }

    public void toggleBold(UUID uuid) {

        if (config.contains("Bold." + uuid.toString())) {
            config.set("Bold." + uuid, null);
        } else {
            config.set("Bold." + uuid, true);
        }

        saveConfig();
    }

    public boolean isBold(UUID uuid) {
        return config.contains("Bold." + uuid.toString());
    }

    public void toggleItalic(UUID uuid) {

        if (config.contains("Italic." + uuid.toString())) {
            config.set("Italic." + uuid, null);
        } else {
            config.set("Italic." + uuid, true);
        }
    }

    public boolean isItalic(UUID uuid) {
        return config.contains("Italic." + uuid.toString());
    }

    private void saveConfig() {
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
