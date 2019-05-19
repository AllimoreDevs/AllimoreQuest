package taurasi.marc.allimorequest;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Config.ConfigWrapper;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerQuestData {
    private OfflinePlayer offlinePlayer;
    private QuestJournal questJorunal;
    private QuestJournalGUI questJournalGUI;

    public PlayerQuestData(Player offlinePlayer){
        this.offlinePlayer = offlinePlayer;
        questJorunal = new QuestJournal(this);
        questJournalGUI = new QuestJournalGUI(this);
    }
    public PlayerQuestData(FileConfiguration config, String uuid){
        UUID id = UUID.fromString(uuid);
        offlinePlayer = Allimorequest.INSTANCE.getServer().getOfflinePlayer(id);
        questJorunal =  new QuestJournal(config, uuid + ".", this);
        questJournalGUI = new QuestJournalGUI(this);
    }
    public void WriteToConfig(FileConfiguration config){
        UUID uniqueId = offlinePlayer.getUniqueId();
        config.set(uniqueId + ".Name", offlinePlayer.getName());
        questJorunal.WriteToConfig(config, uniqueId.toString());
    }

    public void AcceptQuest(Quest quest){
       if( !(questJorunal.AddQuestToJournal(quest)) ){
           AllimoreLogger.LogInfo(ConfigWrapper.INFO_CANNOT_ACCEPT_QUEST_NO_FREE_SLOTS, GetOnlinePlayer());
        }

        quest.notificationService.PlayStartNotification();
    }

    public void AbandonQuest(Quest quest){
        questJorunal.RemoveQuestFromJournal(quest);
        quest.notificationService.PlayAbandonNotification();
    }
    public void AbandonQuest(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return;
        AbandonQuest(quest);
    }

    public void CompleteQuest(Quest quest){
        quest.notificationService.PlayCompleteNotification();
        questJorunal.RemoveQuestFromJournal(quest);
        // TODO: Issues out Quest Reward
    }
    public void CompleteQuest(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return;
        CompleteQuest(quest);
    }

    public boolean TryCompleteQuestObjective(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return false;
        return TryCompleteQuestObjective(quest);
    }
    public boolean TryCompleteQuestObjective(Quest quest){
        boolean succesful = quest.TryCompleteCurrentObjective();
        if(succesful) {
            CompleteQuest(quest);
        }else{
            quest.notificationService.DisplayQuestBriefInChat(GetOnlinePlayer());
            AllimoreLogger.LogInfo( ConfigWrapper.INFO_CANNOT_COMPLETE_QUEST, GetOnlinePlayer());
        }
        return succesful;
    }

    public void SendQuestsToChat(){
        if(questJorunal.TrySendQuestsToChat()){
           AllimoreLogger.LogInfo(ConfigWrapper.INFO_EMPTY_QUEST_JOURNAL, GetOnlinePlayer());
        }
    }
    public void SendQuestStatusToChat(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return;
        quest.notificationService.DisplayQuestBriefInChat(GetOnlinePlayer());
    }

    public void OpenJournalGUI(){
        questJournalGUI.OpenGUI(GetOnlinePlayer());
    }

    // Getters and Setters
    public ArrayList<String> GetQuestNames(){
        return questJorunal.GetQuestNames();
    }
    public Quest[] GetQuests(){
        return questJorunal.GetQuests();
    }
    public OfflinePlayer GetPlayer(){
        return offlinePlayer;
    }
    public Player GetOnlinePlayer(){
        if(offlinePlayer.isOnline()){
            return Allimorequest.INSTANCE.getServer().getPlayer(offlinePlayer.getUniqueId());
        }else{
            AllimoreLogger.LogError("Player is not currently online!");
            return null;
        }
    }
}
