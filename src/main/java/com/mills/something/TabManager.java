package com.mills.something;

import com.mills.something.commands.VanishCommand;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class TabManager implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.getPlayer().setPlayerListHeaderFooter(

                ChatColor.RED + "SERVER",
                PlaceholderAPI.setPlaceholders(e.getPlayer(), "Ping: %player_ping%"));

        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        //rank
        Team teamrank = scoreboard.getTeam("Luckperms") != null ? scoreboard.getTeam("Luckperms") : scoreboard.registerNewTeam("Luckperms");
        teamrank.setPrefix(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(e.getPlayer(), "%luckperms_prefix% ")));

        //vanish
        Team teamvanish = scoreboard.getTeam("Vanish") != null ? scoreboard.getTeam("Vanish") : scoreboard.registerNewTeam("Vanish");
        teamvanish.setSuffix(ChatColor.translateAlternateColorCodes('&', " &4[&cV&4]"));

        //setting vanish
        if (VanishCommand.vanished.contains(e.getPlayer().getUniqueId())) {

            teamvanish.addEntry(e.getPlayer().getName());
        }

        //setting rank
        if (PlaceholderAPI.setPlaceholders(e.getPlayer(), "%luckperms_prefix%") != "") {
            teamrank.addEntry(e.getPlayer().getName());
        }
    }

}
