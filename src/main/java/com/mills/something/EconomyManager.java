package com.mills.something;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.UUID;

public class EconomyManager implements Economy, Listener {

    private BalanceDataManager balanceDataManager;
    private Main main;

    public EconomyManager(Main main) {
        this.main = main;
        this.balanceDataManager = main.getBalanceDataManager();
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        return balanceDataManager.getBalance(player.getUniqueId());
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        if (amount < 0) {
            return new EconomyResponse(0, getBalance(player), EconomyResponse.ResponseType.FAILURE, "Amount cannot be negative.");
        }
        double currentBalance = getBalance(player);
        double newBalance = currentBalance + amount;
        balanceDataManager.setBalance(player.getUniqueId(), newBalance);
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {
        double currentBalance = getBalance(player);
        double newBalance = currentBalance - amount;
        if (amount < 0) {
            return new EconomyResponse(0, currentBalance, EconomyResponse.ResponseType.FAILURE, "Amount cannot be negative");
        }
        if (currentBalance < amount) {
            return new EconomyResponse(0 ,currentBalance, EconomyResponse.ResponseType.FAILURE, "Insufficent funds.");
        }
        if (newBalance < 0) {
            return new EconomyResponse(0 ,currentBalance, EconomyResponse.ResponseType.FAILURE, "Insufficent funds.");
        }
        return new EconomyResponse(amount, newBalance, EconomyResponse.ResponseType.SUCCESS, null);
    }

    @Override
    public String format(double amount) {
        return "$" + String.format("%.2f", amount);
    }

    @Override
    public String currencyNamePlural() {
        return "Dollar";
    }

    @Override
    public String currencyNameSingular() {
        return "Dollars";
    }

    public int fractionalDigits() {
        return 3;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!balanceDataManager.contains(uuid)) {
            balanceDataManager.setBalance(uuid, 0.0);
        }
    }

    //not used methods
    @Override public boolean createPlayerAccount(OfflinePlayer player) { return true; }
    @Override public boolean createPlayerAccount(String s, String s1) { return false; }
    @Override public boolean createPlayerAccount(OfflinePlayer player, String worldName) { return true; }
    @Override public boolean hasBankSupport() { return false; }
    @Override public EconomyResponse createBank(String name, OfflinePlayer player) { return null; }
    @Override public EconomyResponse deleteBank(String name) { return null; }
    @Override public EconomyResponse bankBalance(String name) { return null; }
    @Override public EconomyResponse bankHas(String s, double v) { return null; }
    @Override public EconomyResponse bankDeposit(String name, double amount) { return null; }
    @Override public EconomyResponse isBankOwner(String s, String s1) { return null; }
    @Override public EconomyResponse bankWithdraw(String name, double amount) { return null; }
    @Override public EconomyResponse isBankOwner(String name, OfflinePlayer player) { return null; }
    @Override public EconomyResponse isBankMember(String s, String s1) { return null; }
    @Override public EconomyResponse isBankMember(String name, OfflinePlayer player) { return null; }
    @Override public List<String> getBanks() { return null; }
    @Override public boolean createPlayerAccount(String s) { return false; }
    @Override public String getName() { return null; }
    @Override public boolean hasAccount(String s) { return false; }
    @Override public boolean hasAccount(String s, String s1) { return false; }
    @Override public boolean hasAccount(OfflinePlayer offlinePlayer, String s) { return false; }
    @Override public double getBalance(String s) { return 0; }
    @Override public double getBalance(String s, String s1) { return 0; }
    @Override public double getBalance(OfflinePlayer offlinePlayer, String s) { return 0; }
    @Override public boolean has(String s, double v) { return false; }
    @Override public boolean has(OfflinePlayer offlinePlayer, double v) { return false; }
    @Override public boolean has(String s, String s1, double v) { return false; }
    @Override public boolean has(OfflinePlayer offlinePlayer, String s, double v) { return false; }
    @Override public EconomyResponse withdrawPlayer(String s, double v) { return null; }
    @Override public EconomyResponse depositPlayer(String s, String s1, double v) { return null; }
    @Override public EconomyResponse depositPlayer(OfflinePlayer offlinePlayer, String s, double v) { return null; }
    @Override public EconomyResponse createBank(String s, String s1) { return null; }
    @Override public EconomyResponse withdrawPlayer(String s, String s1, double v) { return null; }
    @Override public EconomyResponse withdrawPlayer(OfflinePlayer offlinePlayer, String s, double v) { return null; }
    @Override public EconomyResponse depositPlayer(String s, double v) { return null; }
    @Override public boolean hasAccount(OfflinePlayer player) { return true; }
}
