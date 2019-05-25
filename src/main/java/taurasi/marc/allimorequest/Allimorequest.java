package taurasi.marc.allimorequest;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
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
        saveResource("config.yml", ConfigWrapper.NeedsUpdate(getConfig()));
        ConfigWrapper.ReadFromConfig(getConfig());

        INSTANCE = this;
        questFactory = new QuestFactory(new ProfessionMaterials(), difficultyManager);
        playerDataIndex = new PlayerDataIndex(questFactory);
        difficultyManager = new DifficultyManager();


        RegisterListeners();
        RegisterCommands();
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

    public static Allimorequest GetInstance(){
        return INSTANCE;
    }
}
