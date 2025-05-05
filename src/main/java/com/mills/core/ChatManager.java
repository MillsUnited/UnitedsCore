package com.mills.core;

import com.mills.core.PlaytimeUtils.PlaytimeUtils;
import com.mills.core.commands.ChatColorCommand;
import com.mills.core.commands.Economy.BalanceDataManager;
import com.mills.core.commands.Economy.EconomyManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.UUID;

public class ChatManager implements Listener {

    private Main main;
    private ChatcolorManager chatcolorManager;
    private BalanceDataManager balanceDataManager;
    private EconomyManager economyManager;

    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTime = 3000;

    public ChatManager(Main main) {
        this.main = main;
        this.chatcolorManager = main.getChatcolorManager();
        this.balanceDataManager = main.getBalanceDataManager();
        this.economyManager = main.getEconomyManager();
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
        String playerDisplay = ChatColor.translateAlternateColorCodes('&', e.getPlayer().getDisplayName());
        String prefix = PlaceholderAPI.setPlaceholders(e.getPlayer(), "%luckperms_prefix%");

        //get color
        String chatcolor = chatcolorManager.getChatcolor(uuid);
        if (chatcolor == null) chatcolor = "";
        boolean isBold = chatcolorManager.isBold(uuid);
        boolean isItalic = chatcolorManager.isItalic(uuid);
        String playerMessage = e.getMessage();
        String ChatcolorMessage = (isBold ? "<bold>" : "") +
                (isItalic ? "<italic>" : "") +
                HexFormatter.parseHexColor(chatcolor) +
                playerMessage +
                "<reset>";

        String format;
        if (!prefix.isEmpty()) {
            format = HexFormatter.parseHexColor(prefix);
            format = "<dark_gray>[</dark_gray>" + format + "<reset><dark_gray>]<reset>";
        } else {
            format = "<dark_gray>[<gray>Player<dark_gray>]<reset>";
        }

        e.setCancelled(true);

        MiniMessage mini = MiniMessage.miniMessage();

        String displayClean = ChatColor.stripColor(playerDisplay);
        displayClean = HexFormatter.parseHexColor(displayClean);

        String fullMessage = format.replace(" ", "") + " <white>" + displayClean + "</white><white>: </white>" + HexFormatter.parseHexColor(ChatcolorMessage);
        Component chatComponent = mini.deserialize(fullMessage)
                .clickEvent(net.kyori.adventure.text.event.ClickEvent.suggestCommand("/msg " + playerName + " "));

        String skyblock = PlaceholderAPI.setPlaceholders(e.getPlayer(), "%superior_island_raw_worth_format%");
        double rawBalance = balanceDataManager.getBalance(uuid);
        String betterBalance = rawBalance % 1 == 0 ? String.valueOf((long) rawBalance) : String.valueOf(rawBalance);

        String fullHoverMessage = format.replace(" ", "") + " <white>" + playerName + "</white><white>: </white>" + HexFormatter.parseHexColor(ChatcolorMessage);

        String hoverText = fullHoverMessage + "\n\n" +
                "<green><bold>INFO:</bold></green>\n" +
                "<green>•</green> <white>Balance: </white><green>" + economyManager.format(Double.parseDouble(betterBalance)) + "</green>\n" +
                "<green>•</green> <white>Playtime: </white><green>" + Utils.formatTime(PlaytimeUtils.getPlaytimeMilis(e.getPlayer())) + "</green>\n" +
                "<green>•</green> <white>Island Value: </white><green>" + skyblock + "</green>\n\n" +
                "<green><bold>CLICK TO SEND MESSAGE!</bold></green>";

        Component hoverComponent = mini.deserialize(hoverText);

        chatComponent = chatComponent.hoverEvent(net.kyori.adventure.text.event.HoverEvent.showText(hoverComponent));

        Bukkit.getConsoleSender().sendMessage(e.getPlayer().getName() + ": " + e.getMessage());

        Component finalChatComponent = chatComponent;
        e.getPlayer().getServer().getOnlinePlayers().forEach(player -> player.sendMessage(finalChatComponent));
    }

}
