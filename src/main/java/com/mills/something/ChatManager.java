package com.mills.something;

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
import java.util.Objects;
import java.util.UUID;

public class ChatManager implements Listener {

    private Main main;
    private ChatcolorManager chatcolorManager;

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 3000;

    public ChatManager(Main main) {
        this.main = main;
        this.chatcolorManager = main.getChatcolorManager();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {

        UUID uuid = e.getPlayer().getUniqueId();
        if (e.getPlayer().hasPermission("chat.cooldown")) {
            long currentTime = System.currentTimeMillis();
            if (cooldowns.containsKey(uuid)) {
                long lastMessageTime = cooldowns.get(uuid);
                if ((currentTime - lastMessageTime) < cooldownTime) {
                    cooldowns.put(uuid, currentTime);
                    long remainingTime = (cooldownTime - (currentTime - lastMessageTime)) / 1000;
                    e.getPlayer().sendMessage(main.prefix + ChatColor.RED + "You must wait " + remainingTime + " more seconds to chat!");
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
            format = ChatColor.translateAlternateColorCodes('&', main.getMessagesManager().getMessage("chat.rank.has-rank").replace("%luckperms_prefix%", prefix));
        } else {
            format = ChatColor.translateAlternateColorCodes('&', main.getMessagesManager().getMessage("chat.rank.no-rank"));
        }

        e.setCancelled(true);

        TextComponent chatformat = new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getMessagesManager().getMessage("chat.format")
                .replace("<rank>", format)
                .replace("<player>", playerDisplay)
                .replace("<message>", ChatcolorMessage)));

        chatformat.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + playerName + " "));

        TextComponent hoverformat = new TextComponent(ChatColor.translateAlternateColorCodes('&', main.getMessagesManager().getMessage("chat.format")
                .replace("<rank>", format)
                .replace("<player>", playerName)
                .replace("<message>", ChatcolorMessage)));

        String hoverMessageLine1 = main.getMessagesManager().getMessage("chat.hover.line1").replace("<format>", hoverformat.getText()).replace("\\n", "\n");
        String hoverMessageLine2 = main.getMessagesManager().getMessage("chat.hover.line2").replace("\\n", "\n");
        String hoverMessageLine3 = main.getMessagesManager().getMessage("chat.hover.line3").replace("\\n", "\n");

        TextComponent hover1 = new TextComponent(ChatColor.translateAlternateColorCodes('&', hoverMessageLine1));
        TextComponent hover2 = new TextComponent(ChatColor.translateAlternateColorCodes('&', hoverMessageLine2));
        TextComponent hover3 = new TextComponent(ChatColor.translateAlternateColorCodes('&', hoverMessageLine3));

        BaseComponent[] hoverMessage = new BaseComponent[]{hover1, hover2, hover3};

        chatformat.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverMessage));

        e.getPlayer().getServer().getOnlinePlayers().forEach(player -> player.spigot().sendMessage(chatformat));


    }

}
