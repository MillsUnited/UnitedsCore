package com.mills.core.commands.teleport.tpa;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class TpaManager {

    private final Map<Player, Player> tpaRequests = new HashMap<>();
    private final Map<Player, Boolean> tpaToggled = new HashMap<>();
    private final Map<Player, Long> lastTpaRequest = new HashMap<>();
    private final long COOLDOWN_TIME = 30000;
    public String prefix = ChatColor.translateAlternateColorCodes('&', "&b&lTPA &8» &7");

    public boolean sendTpaRequest(Player requester, Player target) {
        if (tpaToggled.getOrDefault(target, false)) {
            requester.sendMessage(prefix + target.getName() + " has disabled TPA requests.");
            return false;
        }

        tpaRequests.put(target, requester);
        target.sendMessage(prefix + requester.getName() + " has requested to teleport to you. Type /tpaaccept to accept or /tpadeny to deny.");
        requester.sendMessage(prefix + "Your teleport request has been sent to " + target.getName());

        lastTpaRequest.put(requester, System.currentTimeMillis());
        return true;
    }

    public boolean canSendRequest(Player player) {
        if (lastTpaRequest.containsKey(player)) {
            long timeSinceLastRequest = System.currentTimeMillis() - lastTpaRequest.get(player);
            if (timeSinceLastRequest < COOLDOWN_TIME) {
                long timeLeft = (COOLDOWN_TIME - timeSinceLastRequest) / 1000;
                player.sendMessage(prefix + "You must wait " + timeLeft + " seconds before sending another TPA request.");
                return false;
            }
        }
        return true;
    }

    public boolean acceptTpaRequest(Player player) {
        if (tpaRequests.containsKey(player)) {
            Player requester = tpaRequests.get(player);
            tpaRequests.remove(player);
            requester.teleport(player);
            requester.sendMessage(prefix + player.getName() + " has accepted your teleport request.");
            player.sendMessage(prefix + "You have accepted " + requester.getName() + "'s teleport request.");

            lastTpaRequest.remove(requester);
            return true;
        } else {
            player.sendMessage(prefix + "You do not have any TPA requests.");
            return false;
        }
    }

    public boolean denyTpaRequest(Player player) {
        if (tpaRequests.containsKey(player)) {
            Player requester = tpaRequests.get(player);
            tpaRequests.remove(player);
            requester.sendMessage(prefix + player.getName() + " has denied your teleport request.");
            player.sendMessage(prefix + "You have denied the teleport request.");
            return true;
        } else {
            player.sendMessage(prefix + "You do not have any TPA requests.");
            return false;
        }
    }

    public void toggleTpaRequests(Player player) {
        boolean currentStatus = tpaToggled.getOrDefault(player, false);
        tpaToggled.put(player, !currentStatus);
        String statusMessage = currentStatus ? "enabled" : "disabled";
        player.sendMessage(prefix + "You have " + ChatColor.AQUA + statusMessage + ChatColor.GRAY + " receiving TPA requests.");
    }

}
