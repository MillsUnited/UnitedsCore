package com.mills.core;

import com.mills.core.commandTab.*;
import com.mills.core.commands.*;
import com.mills.core.commands.ClearinvCommand;
import com.mills.core.commands.Menus.*;
import com.mills.core.commands.gamemode.*;
import com.mills.core.commands.teleport.*;
import com.mills.core.commands.teleport.homes.*;
import com.mills.core.commands.teleport.offline.OfflinePlayerTeleportCommand;
import com.mills.core.commands.teleport.offline.OfflinePlayerTeleportManager;
import com.mills.core.commands.teleport.tpa.*;
import com.mills.core.commands.teleport.warps.*;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private NicknameManager nicknameManager;
    private ChatcolorManager chatcolorManager;
    private BalanceDataManager balanceDataManager;
    private EconomyManager economyManager;
    private WarpManager warpManager;
    private HomesManager homesManager;
    private OfflinePlayerTeleportManager offlinePlayerTeleportManager;
    public static String prefix = ChatColor.translateAlternateColorCodes('&', "&4&lSERVER &8» &7");
    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;

        nicknameManager = new NicknameManager(this.getDataFolder());
        chatcolorManager = new ChatcolorManager(this.getDataFolder());
        balanceDataManager = new BalanceDataManager(this.getDataFolder());
        warpManager = new WarpManager(this.getDataFolder());
        economyManager = new EconomyManager(this);
        homesManager = new HomesManager(this.getDataFolder());
        offlinePlayerTeleportManager = new OfflinePlayerTeleportManager(this.getDataFolder());

        TpaManager tpaManager = new TpaManager();

        setupEconomy();

        BackCommand backCommand = new BackCommand();
        SpeedCommand speedCommand = new SpeedCommand();

        Bukkit.getPluginManager().registerEvents(nicknameManager, this);
        Bukkit.getPluginManager().registerEvents(economyManager, this);
        Bukkit.getPluginManager().registerEvents(new AntiHungarListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChatManager(this), this);
        Bukkit.getPluginManager().registerEvents(new ChatcolorGUI(this), this);
        Bukkit.getPluginManager().registerEvents(backCommand, this);
        Bukkit.getPluginManager().registerEvents(speedCommand, this);
        Bukkit.getPluginManager().registerEvents(offlinePlayerTeleportManager, this);

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        getCommand("economy").setExecutor(new EconomyCommand(this));
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("ptime").setExecutor(new PTimeCommand());
        getCommand("pweather").setExecutor(new PWeatherCommand());
        getCommand("gmc").setExecutor(new GamemodeCreative());
        getCommand("gms").setExecutor(new GamemodeSurvival());
        getCommand("gmsp").setExecutor(new GamemodeSpectator());
        getCommand("gma").setExecutor(new GamemodeAdventure());
        getCommand("enchant").setExecutor(new EnchantCommand());
        getCommand("fix").setExecutor(new FixCommand());
        getCommand("speed").setExecutor(speedCommand);
        getCommand("top").setExecutor(new TopCommand());
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("seen").setExecutor(new SeenCommand());
        getCommand("ping").setExecutor(new PingCommand());
        getCommand("nick").setExecutor(new NickCommand(this));
        getCommand("near").setExecutor(new NearCommand());
        getCommand("corereload").setExecutor(new ReloadCommand(this));
        getCommand("bal").setExecutor(new BalanceCommand(this));
        getCommand("store").setExecutor(new StoreCommand());
        getCommand("rename").setExecutor(new RenameCommand());
        getCommand("enderchest").setExecutor(new EnderchestCommand());
        getCommand("clearinventory").setExecutor(new ClearinvCommand());
        getCommand("craft").setExecutor(new CraftCommand());
        getCommand("chatcolor").setExecutor(new ChatColorCommand());
        getCommand("sethome").setExecutor(new SetHomeCommand(this));
        getCommand("deletehome").setExecutor(new DeleteHomeCommand(this));
        getCommand("movehome").setExecutor(new MoveHomeCommand(this));
        getCommand("home").setExecutor(new HomeCommand(this));
        getCommand("homes").setExecutor(new HomesCommand(this));
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("teleport").setExecutor(new TeleportCommand());
        getCommand("teleporthere").setExecutor(new TeleportHereCommand());
        getCommand("back").setExecutor(backCommand);
        getCommand("pweather").setTabCompleter(new PWTabComplete());
        getCommand("enchant").setTabCompleter(new EnchantTabComplete());
        getCommand("ptime").setTabCompleter(new PTTabComplete());
        getCommand("heal").setTabCompleter(new HealTabComplete());
        getCommand("economy").setTabCompleter(new EconomyTabComplete());
        getCommand("warp").setExecutor(new WarpCommand(this));
        getCommand("setwarp").setExecutor(new SetWarpCommand(this));
        getCommand("deletewarp").setExecutor(new DelWarpCommand(this));
        getCommand("warps").setExecutor(new WarpsCommand(this));
        getCommand("anvil").setExecutor(new AnvilCommand());
        getCommand("speed").setTabCompleter(new SpeedTabComplete());
        getCommand("offlineteleport").setExecutor(new OfflinePlayerTeleportCommand(this));
        getCommand("playtime").setExecutor(new PlaytimeCommand());
        getCommand("tpa").setExecutor(new TpaCommand(tpaManager));
        getCommand("tpaaccept").setExecutor(new TpaAcceptCommand(tpaManager));
        getCommand("tpadeny").setExecutor(new TpaDenyCommand(tpaManager));
        getCommand("tpatoggle").setExecutor(new TpaToggleCommand(tpaManager));

    }

    public NicknameManager getNicknameManager() {
        return nicknameManager;
    }

    public ChatcolorManager getChatcolorManager() {
        return chatcolorManager;
    }

    public BalanceDataManager getBalanceDataManager() {
        return balanceDataManager;
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    public EconomyManager getEconomyManager() {
        return economyManager;
    }

    private void setupEconomy() {
        getServer().getServicesManager().register(Economy.class, economyManager, this, ServicePriority.High);
    }

    @Override
    public void onDisable() {
        balanceDataManager.saveConfig();

//        for (Player player : Bukkit.getOnlinePlayers()) {
//            offlinePlayerTeleportManager.savePlayerLocation(player);
//        }
    }

    public HomesManager getHomesManager() {
        return homesManager;
    }

    public OfflinePlayerTeleportManager getOfflinePlayerTeleportManager() {
        return offlinePlayerTeleportManager;
    }

    public static Main getInstance() {
        return instance;
    }
}