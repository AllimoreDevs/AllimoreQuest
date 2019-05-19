package taurasi.marc.allimorequest;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorequest.Config.ConfigWrapper;

public final class Allimorequest extends JavaPlugin {

    public static Allimorequest INSTANCE;
    public static PlayerDataIndex PLAYER_DATA;
    public static EventListener EVENT_LISTENER;

    private static CommandManager cmdManager;
    private static TabComplete tabComplete;

    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;
        EVENT_LISTENER = new EventListener();
        PLAYER_DATA = new PlayerDataIndex( new CustomConfig("PlayerData.yml", getDataFolder().getPath(), this));

        getServer().getPluginManager().registerEvents(EVENT_LISTENER, this);
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);

        saveDefaultConfig();
        ConfigWrapper.ReadFromConfig(getConfig());

        cmdManager = new CommandManager();
        this.getCommand("QuestJournal").setExecutor(cmdManager);
        this.getCommand("QuestStatus").setExecutor(cmdManager);
        this.getCommand("GenerateQuest").setExecutor(cmdManager);
        this.getCommand("AbandonQuest").setExecutor(cmdManager);
        this.getCommand("CompleteQuest").setExecutor(cmdManager);
        this.getCommand("ForceCompleteQuest").setExecutor(cmdManager);
        this.getCommand("WriteData").setExecutor(cmdManager);

        tabComplete = new TabComplete();
        this.getCommand("QuestStatus").setTabCompleter(tabComplete);
        this.getCommand("AbandonQuest").setTabCompleter(tabComplete);
        this.getCommand("CompleteQuest").setTabCompleter(tabComplete);
        this.getCommand("ForceCompleteQuest").setTabCompleter(tabComplete);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(EVENT_LISTENER);
        PLAYER_DATA.WriteData();
    }
}
