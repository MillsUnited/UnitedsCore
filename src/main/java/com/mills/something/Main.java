package com.mills.something;

import com.mills.something.commandTab.EnchantTabComplete;
import com.mills.something.commandTab.HealTabComplete;
import com.mills.something.commandTab.PTTabComplete;
import com.mills.something.commandTab.PWTabComplete;
import com.mills.something.commands.*;
import com.mills.something.commands.ClearinvCommand;
import com.mills.something.commands.Menus.CraftCommand;
import com.mills.something.commands.Menus.EnderchestCommand;
import com.mills.something.commands.gamemode.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private NicknameManager nicknameManager;
    private ChatcolorManager chatcolorManager;
    private MessagesManager messagesManager;
    private BalanceDataManager balanceDataManager;
    private EconomyManager economyManager;
    public String prefix;

    @Override
    public void onEnable() {

        nicknameManager = new NicknameManager(this.getDataFolder());
        chatcolorManager = new ChatcolorManager(this.getDataFolder());
        balanceDataManager = new BalanceDataManager(this.getDataFolder());
        economyManager = new EconomyManager(this);

        setupEconomy();

        Bukkit.getPluginManager().registerEvents(nicknameManager, this);
        Bukkit.getPluginManager().registerEvents(economyManager, this);
        Bukkit.getPluginManager().registerEvents(new TabManager(), this);
        Bukkit.getPluginManager().registerEvents(new ChatManager(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatcolorGUI(this), this);

        messagesManager = new MessagesManager(this);
        messagesManager.createMessagesConfig();
        prefix = ChatColor.translateAlternateColorCodes('&', getMessagesManager().getMessage("Prefix"));

        Bukkit.getPluginManager().registerEvents(new EventListener(this), this);

        getCommand("economy").setExecutor(new EconomyCommand(this));
        getCommand("fly").setExecutor(new FlyCommand(this));
        getCommand("heal").setExecutor(new HealCommand(this));
        getCommand("feed").setExecutor(new FeedCommand(this));
        getCommand("ptime").setExecutor(new PTimeCommand(this));
        getCommand("pweather").setExecutor(new PWeatherCommand(this));
        getCommand("gmc").setExecutor(new GamemodeCreative(this));
        getCommand("gms").setExecutor(new GamemodeSurvival(this));
        getCommand("gmsp").setExecutor(new GamemodeSpectator(this));
        getCommand("gma").setExecutor(new GamemodeAdventure(this));
        getCommand("enchant").setExecutor(new EnchantCommand(this));
        getCommand("fix").setExecutor(new FixCommand(this));
        getCommand("speed").setExecutor(new SpeedCommand(this));
        getCommand("top").setExecutor(new TopCommand(this));
        getCommand("hat").setExecutor(new HatCommand(this));
        getCommand("seen").setExecutor(new SeenCommand(this));
        getCommand("ping").setExecutor(new PingCommand(this));
        getCommand("nick").setExecutor(new NickCommand(this));
        getCommand("near").setExecutor(new NearCommand(this));
        getCommand("vanish").setExecutor(new VanishCommand(this));
        getCommand("invsee").setExecutor(new InvseeCommand(this));
        getCommand("corereload").setExecutor(new ReloadCommand(this));
        getCommand("bal").setExecutor(new BalanceCommand(this));
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("clearinventory").setExecutor(new ClearinvCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("chatcolor").setExecutor(new ChatColorCommand());
        getCommand("pweather").setTabCompleter(new PWTabComplete());
        getCommand("enchant").setTabCompleter(new EnchantTabComplete());
        getCommand("ptime").setTabCompleter(new PTTabComplete());
        getCommand("heal").setTabCompleter(new HealTabComplete());
        getCommand("economy").setTabCompleter(new EconomyTabComplete());

    }

    public NicknameManager getNicknameManager() {
        return nicknameManager;
    }

    public ChatcolorManager getChatcolorManager() {
        return chatcolorManager;
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }

    public BalanceDataManager getBalanceDataManager() {
        return balanceDataManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    private void setupEconomy() {
        getServer().getServicesManager().register(Economy.class, economyManager, this, ServicePriority.High);
    }

    public void onDisable() {
        balanceDataManager.saveConfig();
    }
}