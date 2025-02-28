package com.mills.something;

import me.clip.placeholderapi.libs.kyori.adventure.platform.facet.Facet;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.UUID;

public class ChatcolorGUI implements Listener {

    private final Main main;
    private final ChatcolorManager chatcolorManager;

    public ChatcolorGUI(Main main) {
        this.main = main;
        this.chatcolorManager = main.getChatcolorManager();
    }


    public static Inventory createCustomInventory(){

        Inventory cc = Bukkit.createInventory(null, 54, "Chatcolor");

        for (int slot = 0; slot < 54; slot++) {
            cc.setItem(slot, getItemForSlot(slot));
        }

        return cc;
    }

    private static ItemStack getItemForSlot(int slot) {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();

        switch (slot) {
            case 10:
                item = new ItemStack(Material.RED_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Dark Red");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 11:
                item = new ItemStack(Material.RED_WOOL);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Red");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 12:
                item = new ItemStack(Material.YELLOW_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GOLD.toString() + ChatColor.BOLD + "Gold");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 13:
                item = new ItemStack(Material.YELLOW_WOOL);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW.toString() + ChatColor.BOLD + "Yellow");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 14:
                item = new ItemStack(Material.LIME_TERRACOTTA);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_GREEN.toString() + ChatColor.BOLD + "Dark Green");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 15:
                item = new ItemStack(Material.LIME_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GREEN.toString() + ChatColor.BOLD + "Green");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 16:
                item = new ItemStack(Material.LIGHT_BLUE_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.AQUA.toString() + ChatColor.BOLD + "Aqua");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 19:
                item = new ItemStack(Material.CYAN_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_AQUA.toString() + ChatColor.BOLD + "Dark Aqua");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 20:
                item = new ItemStack(Material.BLUE_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_BLUE.toString() + ChatColor.BOLD + "Dark Blue");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 21:
                item = new ItemStack(Material.BLUE_CONCRETE_POWDER);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.BLUE.toString() + ChatColor.BOLD + "Blue");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 22:
                item = new ItemStack(Material.PINK_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Pink");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 23:
                item = new ItemStack(Material.MAGENTA_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_PURPLE.toString() + ChatColor.BOLD + "Magenta");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 24:
                item = new ItemStack(Material.WHITE_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "White");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 25:
                item = new ItemStack(Material.GRAY_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "Gray");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 31:
                item = new ItemStack(Material.LIGHT_GRAY_CONCRETE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.GRAY.toString() + ChatColor.BOLD + "Light Gray");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 48:
                item = new ItemStack(Material.NETHER_STAR);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Bold");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 49:
                item = new ItemStack(Material.BARRIER);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.RED.toString() + ChatColor.BOLD + "Close");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            case 50:
                item = new ItemStack(Material.ENDER_EYE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.WHITE.toString() + ChatColor.BOLD + "Italic");
                meta.setLore(Arrays.asList("", ChatColor.GREEN + "Click to toggle Bold text!"));
                break;
            default:
                break;
        }
        item.setItemMeta(meta);
        return item;
    }

    @EventHandler
    public void setChatColor(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;
        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals("Chatcolor")) {
            e.setCancelled(true);

            String colorCode = "";
            String colorName = "";
            UUID uuid = player.getUniqueId();
            String currentStyle = chatcolorManager.getChatcolor(uuid);

            switch (e.getRawSlot()) {
                case 10 -> { colorCode = "&4"; colorName = "Dark Red"; }
                case 11 -> { colorCode = "&c"; colorName = "Red"; }
                case 12 -> { colorCode = "&6"; colorName = "Gold"; }
                case 13 -> { colorCode = "&e"; colorName = "Yellow"; }
                case 14 -> { colorCode = "&2"; colorName = "Dark Green"; }
                case 15 -> { colorCode = "&a"; colorName = "Green"; }
                case 16 -> { colorCode = "&b"; colorName = "Aqua"; }
                case 19 -> { colorCode = "&3"; colorName = "Dark Aqua"; }
                case 20 -> { colorCode = "&1"; colorName = "Dark Blue"; }
                case 21 -> { colorCode = "&9"; colorName = "Blue"; }
                case 22 -> { colorCode = "&d"; colorName = "Pink"; }
                case 23 -> { colorCode = "&5"; colorName = "Purple"; }
                case 24 -> { colorCode = "&f"; colorName = "White"; }
                case 25 -> { colorCode = "&8"; colorName = "Gray"; }
                case 31 -> { colorCode = "&7"; colorName = "Light Gray"; }
                case 48 -> {
                    chatcolorManager.toggleBold(uuid);

                    if (chatcolorManager.isBold(uuid)) {
                        player.sendMessage(main.prefix + "Enabled Bold");
                    } else {
                        player.sendMessage(main.prefix + "Disabled Bold");
                    }
                    player.closeInventory();
                    return;
                }
                case 49 -> {
                    player.closeInventory();
                    return;}
                case 50 -> {
                    chatcolorManager.toggleItalic(uuid);

                    if (chatcolorManager.isItalic(uuid)) {
                        player.sendMessage(main.prefix + "Enabled Italic");
                    } else {
                        player.sendMessage(main.prefix + "Disabled Italic");
                    }
                    player.closeInventory();
                    return;
                }
                default -> { return; }
            }

            applyChatColor(player, colorCode);

            player.sendMessage(main.prefix + "Selected " + ChatColor.translateAlternateColorCodes('&', colorCode + colorName) +
                    ChatColor.GRAY + " Chatcolor!");

            player.closeInventory();
        }
    }

    private void applyChatColor(Player player, String color) {
        UUID uuid = player.getUniqueId();
        chatcolorManager.saveChatcolor(uuid, color);
    }

}