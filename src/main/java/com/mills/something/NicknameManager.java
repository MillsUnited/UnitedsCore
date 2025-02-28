package com.mills.something;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class NicknameManager implements Listener {

    private final File file;
    private FileConfiguration config;

    public NicknameManager(File dataFolder) {
        file = new File(dataFolder, "nicknames.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveNickname(UUID uuid, String nickname) {
        config.set(uuid.toString(), nickname);
        saveConfig();
    }

    public String getNickname(UUID uuid) {
        return config.getString(uuid.toString());
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

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        reloadConfig();

        String nickname = getNickname(uuid);

        if (nickname != null) {
            player.setDisplayName(nickname);
        }
    }

}
