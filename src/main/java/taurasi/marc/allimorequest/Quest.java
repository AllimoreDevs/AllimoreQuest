package taurasi.marc.allimorequest;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.Objectives.Objective;
import taurasi.marc.allimorequest.Utils.QuestLogger;

public class Quest {
    private String questGiverName;
    private String name;
    private String summary;
    private Objective currentObjective;
    private PlayerQuestData playerData;

    public QuestNotifications notificationService;

    public Quest(String questGiverName, String name, String summary, PlayerQuestData playerData){
        this.questGiverName = questGiverName;
        this.name = name;
        this.summary = summary;
        this.playerData = playerData;

        notificationService = new QuestNotifications(this);
    }
    public Quest(FileConfiguration config, String path, PlayerQuestData playerData){
        name = config.getString(path + "Name");
        questGiverName = config.getString(path + "Quest Giver");
        summary = config.getString(path + "Summary");
        this.playerData = playerData;
        SetCurrentObjective(Objective.ReadObjective(config, path + "Objective.", this));

        notificationService = new QuestNotifications(this);
    }
    public void WriteToConfig(FileConfiguration config, String section){
        config.set(section + "Name", name);
        config.set(section + "Quest Giver", questGiverName);
        config.set(section + "Summary", summary);
        currentObjective.WriteToConfig(config, String.format("%s.Objective.", section) );
    }

    public boolean TryCompleteCurrentObjective(){
        return currentObjective.IsComplete();
    }
    public void CompleteQuest(){
        playerData.CompleteQuest(this);
    }

    public String QuestFullName(){
        return String.format("%s[%s]", ConfigWrapper.QUEST_TITLE_COLOR, name);
    }
    public String QuestFullSummary() {
        return String.format("%s%s", ConfigWrapper.QUEST_SUMMARY_COLOR, summary);
    }
    public String QuestFullDescription(){
        return String.format("%s %s", QuestFullName(), QuestFullSummary());
    }

    public void SetCurrentObjective(Objective currentObjective){
        this.currentObjective = currentObjective;
    }

    // Getters and Setters
    public String GetQuestGiverName() {
        return questGiverName;
    }
    public String GetQuestName(){
        return name;
    }
    public String GetSummary(){
        return summary;
    }
    public PlayerQuestData GetPlayerData(){
        return playerData;
    }
    public Objective GetCurrentObjective(){
        return currentObjective;
    }

    public OfflinePlayer GetPlayer(){
        return playerData.GetPlayer();
    }
    public Player GetOnlinePlayer(){
        return playerData.GetOnlinePlayer();
    }
}
