package com.mills.core.commands.Economy;

import com.mills.core.Utils;
import org.bukkit.ChatColor;
import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import com.mills.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class BaltopGUI implements Listener {

    private Main main;
    private BalanceDataManager balanceDataManager;

    public BaltopGUI(Main main) {
        this.main = main;
        this.balanceDataManager = main.getBalanceDataManager();
    }

    public Inventory baltop(Player player) {
        Inventory inv = Bukkit.createInventory(player, 54, "InfernalMC » Baltop");

        ItemStack panes = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta paneMeta = panes.getItemMeta();
        paneMeta.setDisplayName(" ");
        panes.setItemMeta(paneMeta);
        for (int i : new int[]{0,1,2,3,4,5,6,7,8,9,10,16,17,18,19,20,21,22,23,24,25,26,27,35,36,44,45,46,47,48,49,50,51,52,53}) {
            inv.setItem(i, panes);
        }

        int[] baltopSpots = {11,12,13,14,15,28,29,30,31,32,33,34,37,38,39,40,41,42,43};

        List<Map.Entry<UUID, Double>> topBalances = getTopBalances();

        for (int index = 0; index < baltopSpots.length; index++) {
            if (index < topBalances.size()) {
                Map.Entry<UUID, Double> entry = topBalances.get(index);
                UUID uuid = entry.getKey();
                double balance = entry.getValue();
                OfflinePlayer topPlayer = Bukkit.getOfflinePlayer(uuid);

                inv.setItem(baltopSpots[index], getPlayerSkull(topPlayer, balance, index + 1));
            } else {
                inv.setItem(baltopSpots[index], getCustomTextureSkull());
            }
        }

        player.openInventory(inv);
        return inv;
    }

    private List<Map.Entry<UUID, Double>> getTopBalances() {
        Map<UUID, Double> balances = new HashMap<>();

        for (String key : balanceDataManager.getAllUUIDs()) {
            try {
                UUID uuid = UUID.fromString(key);
                double balance = balanceDataManager.getBalance(uuid);
                balances.put(uuid, balance);
            } catch (IllegalArgumentException e) {
                // Skip invalid UUIDs
            }
        }

        List<Map.Entry<UUID, Double>> sortedBalances = new ArrayList<>(balances.entrySet());
        sortedBalances.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));

        return sortedBalances;
    }

    private ItemStack getPlayerSkull(OfflinePlayer player, double balance, int place) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        PlayerProfile profile = Bukkit.createProfile(player.getUniqueId());
        meta.setPlayerProfile(profile);

        meta.setDisplayName("§6#" + place + " §e" + player.getName());
        List<String> lore = new ArrayList<>();
        lore.add("§7Balance: §a" + Utils.convertBalance(balance));
        meta.setLore(lore);

        skull.setItemMeta(meta);
        return skull;
    }

    private ItemStack getCustomTextureSkull(){
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        String textures = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjZlNjY0NWE0NDBmNzNiZmI4ZDI3NGY0YTFiMGNkMDI3MWY1MjIzM2FiZDA3YTQ3N2IxZTdkZDdlODRiNGJkIn19fQ==";

        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", textures));

        skullMeta.setPlayerProfile(profile);
        skullMeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Invalid Player");

        skull.setItemMeta(skullMeta);
        return skull;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().equals("InfernalMC » Baltop")) {
            e.setCancelled(true);
        }
    }
}