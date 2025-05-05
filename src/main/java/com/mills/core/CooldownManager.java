package com.mills.core;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private static final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();


    public static boolean isOnCooldown(Player player, String key, long cooldownMillis) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) return false;

        Map<String, Long> playerCooldowns = cooldowns.get(uuid);
        if (!playerCooldowns.containsKey(key)) return false;

        long lastUsed = playerCooldowns.get(key);
        return System.currentTimeMillis() - lastUsed < cooldownMillis;
    }

    public static long getTimeLeft(Player player, String key, long cooldownMillis) {
        UUID uuid = player.getUniqueId();
        long lastUsed = cooldowns.getOrDefault(uuid, new HashMap<>()).getOrDefault(key, 0L);
        return Math.max(0, (cooldownMillis - (System.currentTimeMillis() - lastUsed)));
    }

    public static void setCooldown(Player player, String key) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(key, System.currentTimeMillis());
    }

}
