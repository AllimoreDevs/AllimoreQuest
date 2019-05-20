package taurasi.marc.allimorequest;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.GUI.QuestJournalGUI;
import taurasi.marc.allimorequest.Objectives.KillNearObjective;
import taurasi.marc.allimorequest.Objectives.KillObjective;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerQuestData {
    private OfflinePlayer offlinePlayer;
    private QuestJournal questJorunal;
    private QuestJournalGUI questJournalGUI;

    private ArrayList<EntityType> lockedTypes;

    public PlayerQuestData(Player offlinePlayer){
        this.offlinePlayer = offlinePlayer;
        questJorunal = new QuestJournal(this);
        questJournalGUI = new QuestJournalGUI(9, Allimorequest.GUI_ROUTER,this);
        lockedTypes = new ArrayList<>();
    }
    public PlayerQuestData(FileConfiguration config, String uuid){
        UUID id = UUID.fromString(uuid);
        offlinePlayer = Allimorequest.INSTANCE.getServer().getOfflinePlayer(id);
        questJorunal =  new QuestJournal(config, uuid + ".", this);
        questJournalGUI = new QuestJournalGUI(9, Allimorequest.GUI_ROUTER,this);
        LockTypesFromQuests();
    }
    public void WriteToConfig(FileConfiguration config){
        UUID uniqueId = offlinePlayer.getUniqueId();
        config.set(uniqueId + ".Name", offlinePlayer.getName());
        questJorunal.WriteToConfig(config, uniqueId.toString());
    }

    public boolean ContainsQuestName(String name){
        return questJorunal.ContainsQuestName(name);

    }

    private void LockTypesFromQuests(){
        lockedTypes = new ArrayList<>();

        Quest[] quests = questJorunal.GetQuests();
        for (Quest quest : quests) {
            if (quest == null) continue;
            if (quest.GetCurrentObjective() instanceof KillObjective) {
                KillObjective objective = (KillObjective) quest.GetCurrentObjective();
                LockType(objective.GetEntityType());
            }
        }
    }
    private boolean IsTypeLocked(EntityType type){
        return lockedTypes.contains(type);
    }
    private void LockType(EntityType type){
        if(lockedTypes.contains(type)){
            AllimoreLogger.LogError("Cannot lock type that is already locked!");
            return;
        }
        lockedTypes.add(type);
    }
    private void UnlockType(EntityType type){
        if(!lockedTypes.contains(type)){
            AllimoreLogger.LogError("Cannot Unlock type, type not found in locked types!");
            return;
        }
        lockedTypes.remove(type);
    }

    public void AcceptQuest(Quest quest){
       if(questJorunal.ContainsQuestName(quest.GetQuestName())){
           AllimoreLogger.LogInfo(ConfigWrapper.INFO_CANNOT_ACCEPT_QUEST_SAME_NAME, GetOnlinePlayer());
           return;
       }
       if(quest.GetCurrentObjective() instanceof KillObjective){
           KillObjective objective = (KillObjective) quest.GetCurrentObjective();
           if(IsTypeLocked(objective.GetEntityType())){
               AllimoreLogger.LogInfo(ConfigWrapper.INFO_CANNOT_ACCEPT_QUEST_SAME_ENTITY, GetOnlinePlayer());
               return;
           }
       }

       if( !(questJorunal.AddQuestToJournal(quest)) ){
           AllimoreLogger.LogInfo(ConfigWrapper.INFO_CANNOT_ACCEPT_QUEST_NO_FREE_SLOTS, GetOnlinePlayer());
           return;
       }

       quest.notificationService.PlayStartNotification();
    }

    public void AbandonQuest(Quest quest){
        questJorunal.RemoveQuestFromJournal(quest);
        quest.GetCurrentObjective().Disable();

        if(quest.GetCurrentObjective() instanceof KillObjective){
            KillObjective objective = (KillObjective)quest.GetCurrentObjective();
            UnlockType(objective.GetEntityType());
        }

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
        if(quest.GetCurrentObjective() instanceof KillObjective){
            KillObjective objective = (KillObjective)quest.GetCurrentObjective();
            UnlockType(objective.GetEntityType());
        }
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
