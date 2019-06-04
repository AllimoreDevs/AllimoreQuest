package taurasi.marc.allimorequest;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.GUI.GUIEventRouter;
import taurasi.marc.allimorequest.Commands.CommandManager;
import taurasi.marc.allimorequest.Commands.QuestCommandTabComplete;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.Observers.BlockListener;
import taurasi.marc.allimorequest.Observers.EventListener;
import taurasi.marc.allimorequest.Observers.PlayerConnectionListener;
import taurasi.marc.allimorequest.ProcGen.DifficultyManager;
import taurasi.marc.allimorequest.ProcGen.QuestFactory;
import taurasi.marc.allimorequest.Professions.ProfessionMaterials;

public final class Allimorequest extends JavaPlugin {
    private static Allimorequest INSTANCE;
    private Economy economy;

    public static EventListener EVENT_LISTENER;
    public static GUIEventRouter GUI_ROUTER;

    // Core Manager Singletons
    private PlayerDataIndex playerDataIndex;
    private QuestFactory questFactory;
    public CommandManager cmdManager;
    private DifficultyManager difficultyManager;

    private PlayerConnectionListener playerConnectionListener;
    private BlockListener blockListener;

    @Override
    public void onEnable() {
        if(!SetupEconomy()){
            AllimoreLogger.LogError("No Vault Dependancy Found! Disabling plugin");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        saveResource("config.yml", ConfigWrapper.NeedsUpdate(getConfig()));
        ConfigWrapper.ReadFromConfig(getConfig());

        INSTANCE = this;
        difficultyManager = new DifficultyManager();
        questFactory = new QuestFactory(new ProfessionMaterials(), difficultyManager);
        playerDataIndex = new PlayerDataIndex(questFactory);

        RegisterListeners();
        RegisterCommands();
    }

    private boolean SetupEconomy() {
        if(Bukkit.getPluginManager().getPlugin("Vault") == null){
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if(rsp == null){
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    @Override
    public void onDisable() {
        UnregisterListeners();

        playerDataIndex.WriteData();
    }

    private void RegisterCommands() {
        cmdManager = new CommandManager(playerDataIndex, questFactory, difficultyManager);
        QuestCommandTabComplete tabComplete = new QuestCommandTabComplete(cmdManager, difficultyManager, playerDataIndex);

        this.getCommand("Quest").setExecutor(cmdManager);
        this.getCommand("Quest").setTabCompleter(tabComplete);
    }

    private void InitListeners() {
        EVENT_LISTENER = new EventListener();
        playerConnectionListener = new PlayerConnectionListener(playerDataIndex);
        blockListener = new BlockListener(playerDataIndex);
    }
    private void RegisterListeners(){
        InitListeners();

        getServer().getPluginManager().registerEvents(EVENT_LISTENER, this);
        GUI_ROUTER = new GUIEventRouter(this);
        getServer().getPluginManager().registerEvents(playerConnectionListener, this);
        getServer().getPluginManager().registerEvents(blockListener, this);
    }

    private void UnregisterListeners(){
        HandlerList.unregisterAll(EVENT_LISTENER);
        HandlerList.unregisterAll(GUI_ROUTER);
        HandlerList.unregisterAll(playerConnectionListener);
        HandlerList.unregisterAll(blockListener);

    }

    // Getters and Setters
    public QuestFactory GetQuestFactory(){
        return questFactory;
    }
    public Economy GetEconomy() {
        return economy;
    }

    public static Allimorequest GetInstance(){
        return INSTANCE;
    }
}
