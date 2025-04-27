package com.mills.core;

import com.mills.core.PlaytimeUtils.PlaytimeUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.UUID;

public class ChatManager implements Listener {

    private Main main;
    private ChatcolorManager chatcolorManager;
    private BalanceDataManager balanceDataManager;

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 3000;

    public ChatManager(Main main) {
        this.main = main;
        this.chatcolorManager = main.getChatcolorManager();
        this.balanceDataManager = main.getBalanceDataManager();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        UUID uuid = e.getPlayer().getUniqueId();
        if (e.getPlayer().hasPermission("server.chat.cooldown")) {
            long currentTime = System.currentTimeMillis();
            if (cooldowns.containsKey(uuid)) {
                long lastMessageTime = cooldowns.get(uuid);
                if ((currentTime - lastMessageTime) < cooldownTime) {
                    cooldowns.put(uuid, currentTime);
                    long remainingTime = (cooldownTime - (currentTime - lastMessageTime)) / 1000;
                    e.getPlayer().sendMessage(Main.prefix + ChatColor.RED + "You must wait " + remainingTime + " more seconds to chat!");
                    e.setCancelled(true);
                    return;
                }
            }
            cooldowns.put(uuid, currentTime);
        }

        String playerName = e.getPlayer().getName();
        String playerDisplay = e.getPlayer().getDisplayName();
        String prefix = PlaceholderAPI.setPlaceholders(e.getPlayer(), "%luckperms_prefix%");

        //get color
        String chatcolor = chatcolorManager.getChatcolor(uuid);
        if (chatcolor == null) chatcolor = "";
        String formattedColor = ChatColor.translateAlternateColorCodes('&', chatcolor);
        boolean isBold = chatcolorManager.isBold(uuid);
        boolean isItalic = chatcolorManager.isItalic(uuid);
        String playerMessage = e.getMessage();
        String ChatcolorMessage = formattedColor + (isBold ? ChatColor.BOLD : "") + (isItalic ? ChatColor.ITALIC : "") + playerMessage + ChatColor.RESET;
        e.setMessage(ChatcolorMessage);

        //format for rank/player tag
        String format;
        if (prefix != null && !prefix.isEmpty()) {
            format = ChatColor.translateAlternateColorCodes('&', "&8[&r%luckperms_prefix%&8]&r");
        } else {
            format = ChatColor.translateAlternateColorCodes('&', "&8[&7Player&8]&r");
        }

        e.setCancelled(true);


        TextComponent chatformat = new TextComponent(ChatColor.translateAlternateColorCodes('&', format + " " + playerDisplay + "&f: &r" + ChatcolorMessage));

        chatformat.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerName + " "));

        TextComponent hoverformat = new TextComponent(ChatColor.translateAlternateColorCodes('&', format + " " + playerName + "&f: &r" + ChatcolorMessage));

        String skyblock = PlaceholderAPI.setPlaceholders(e.getPlayer(), "%superior_island_raw_worth_format%");

        String betterBalance;
        if ((balanceDataManager.getBalance(uuid)).toString().endsWith(".0")) {
            betterBalance = (balanceDataManager.getBalance(uuid)).toString().replace(".0", "");
        } else {
            betterBalance = (balanceDataManager.getBalance(uuid)).toString();
        }

        String hoverMessageLine1 = hoverformat.getText();
        String hoverMessageLine2 = "";
        String hoverMessageLine3 = "&a&lINFO:";
        String hoverMessageLine5 = "&a• &fBalance: &a$" + betterBalance;
        String hoverMessageLine6 = "&a• &fPlaytime: &a" + PlaytimeUtils.formatPlaytime(PlaytimeUtils.getPlaytimeMilis(e.getPlayer()));
        String hoverMessageLine7 = "&a• &fIsland Value: &a" + skyblock;
        String hoverMessageLine8 = "";
        String hoverMessageLine9 = "&a&lCLICK TO SEND MESSAGE!";

        String combinedHover = hoverMessageLine1 + "\n" + hoverMessageLine2 + "\n" + hoverMessageLine3 + "\n" +
                hoverMessageLine5 + "\n" + hoverMessageLine6 + "\n" + hoverMessageLine7 + "\n" + hoverMessageLine8 + "\n" + hoverMessageLine9;
        TextComponent hoverText = new TextComponent(ChatColor.translateAlternateColorCodes('&', combinedHover));
        chatformat.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{hoverText}));

        e.getPlayer().getServer().getOnlinePlayers().forEach(player -> player.spigot().sendMessage(chatformat));


    }

}
