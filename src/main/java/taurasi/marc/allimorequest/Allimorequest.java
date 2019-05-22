package taurasi.marc.allimorequest;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorecore.GUI.GUIEventRouter;
import taurasi.marc.allimorequest.Commands.CommandManager;
import taurasi.marc.allimorequest.Commands.GenerateQuestTabComplete;
import taurasi.marc.allimorequest.Commands.QuestNameTabComplete;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.Database.DatabaseManager;
import taurasi.marc.allimorequest.Observers.EventListener;
import taurasi.marc.allimorequest.Observers.PlayerConnectionListener;
import taurasi.marc.allimorequest.ProcGen.DifficultyManager;
import taurasi.marc.allimorequest.ProcGen.QuestFactory;
import taurasi.marc.allimorequest.Professions.ProfessionMaterials;

public final class Allimorequest extends JavaPlugin {

    public static Allimorequest INSTANCE;
    public static ProfessionMaterials PROFESSION_MATERIALS;
    public static PlayerDataIndex PLAYER_DATA;
    public static EventListener EVENT_LISTENER;
    public static GUIEventRouter GUI_ROUTER;
    public static QuestFactory QUEST_FACTORY;
    public static DifficultyManager DIFFICULTY_MANAGER;

    private CommandManager cmdManager;
    private QuestNameTabComplete tabComplete;
    private GenerateQuestTabComplete generateQuestTabComplete;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        ConfigWrapper.ReadFromConfig(getConfig());

        INSTANCE = this;
        PROFESSION_MATERIALS = new ProfessionMaterials();
        EVENT_LISTENER = new EventListener();
        GUI_ROUTER = new GUIEventRouter(this);
        PLAYER_DATA = new PlayerDataIndex( new CustomConfig("PlayerData.yml", getDataFolder().getPath(), this));
        DIFFICULTY_MANAGER = new DifficultyManager();
        QUEST_FACTORY = new QuestFactory();

        getServer().getPluginManager().registerEvents(EVENT_LISTENER, this);
        getServer().getPluginManager().registerEvents(new PlayerConnectionListener(), this);

        cmdManager = new CommandManager();
        this.getCommand("QuestJournal").setExecutor(cmdManager);
        this.getCommand("QuestStatus").setExecutor(cmdManager);
        this.getCommand("GenerateQuest").setExecutor(cmdManager);
        this.getCommand("AbandonQuest").setExecutor(cmdManager);
        this.getCommand("CompleteQuest").setExecutor(cmdManager);
        this.getCommand("ForceCompleteQuest").setExecutor(cmdManager);
        this.getCommand("WriteData").setExecutor(cmdManager);

        tabComplete = new QuestNameTabComplete();
        this.getCommand("QuestStatus").setTabCompleter(tabComplete);
        this.getCommand("AbandonQuest").setTabCompleter(tabComplete);
        this.getCommand("CompleteQuest").setTabCompleter(tabComplete);
        this.getCommand("ForceCompleteQuest").setTabCompleter(tabComplete);

        generateQuestTabComplete = new GenerateQuestTabComplete();
        this.getCommand("GenerateQuest").setTabCompleter(generateQuestTabComplete);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll(EVENT_LISTENER);
        PLAYER_DATA.WriteData();
    }
}
