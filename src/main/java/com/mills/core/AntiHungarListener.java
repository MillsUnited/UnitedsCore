package com.mills.core;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class AntiHungarListener implements Listener {

    @EventHandler
    public void onAntiHungar(FoodLevelChangeEvent e) {
        HumanEntity entiy = e.getEntity();
        if (entiy instanceof Player) {
            Player player = (Player) entiy;
            if (player.hasPermission("server.antihungar")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (e.getPlayer().hasPermission("server.antihungar")) {
            e.getPlayer().setFoodLevel(20);
            e.getPlayer().setSaturation(20f);
        }
    }

}
