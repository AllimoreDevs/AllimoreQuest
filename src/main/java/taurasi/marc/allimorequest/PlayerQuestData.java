package taurasi.marc.allimorequest;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.GUI.QuestBoardGUI;
import taurasi.marc.allimorequest.GUI.QuestJournalGUI;
import taurasi.marc.allimorequest.Objectives.KillObjective;
import taurasi.marc.allimorequest.ProcGen.QuestCollection;
import taurasi.marc.allimorequest.ProcGen.QuestFactory;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerQuestData implements QuestCollection {
    private OfflinePlayer offlinePlayer;
    private QuestJournal questJorunal;
    private QuestJournalGUI questJournalGUI;
    private QuestBoardGUI questBoardGUI;
    private EntityLock entityLocker;

    // Construct New
    public PlayerQuestData(Player offlinePlayer ,QuestFactory questFactory){
        this.offlinePlayer = offlinePlayer;
        questJorunal = new QuestJournal(this);
        questJournalGUI = new QuestJournalGUI(9, Allimorequest.GUI_ROUTER,this);
        questBoardGUI = new QuestBoardGUI(Allimorequest.GUI_ROUTER, this, questFactory);
        entityLocker = new EntityLock();
    }
    // Serialization
    // Re-Construct from Config
    public PlayerQuestData(FileConfiguration config, String uuid, QuestFactory questFactory){
        UUID id = UUID.fromString(uuid);
        offlinePlayer = Allimorequest.GetInstance().getServer().getOfflinePlayer(id);
        questJorunal =  new QuestJournal(config, uuid + ".", this);
        questJournalGUI = new QuestJournalGUI(9, Allimorequest.GUI_ROUTER,this);
        questBoardGUI = new QuestBoardGUI(Allimorequest.GUI_ROUTER, this, questFactory);
        entityLocker = new EntityLock();
        entityLocker.LockTypesFromQuests(questJorunal);
    }
    public void WriteToConfig(FileConfiguration config){
        UUID uniqueId = offlinePlayer.getUniqueId();
        config.set(uniqueId + ".Name", offlinePlayer.getName());
        questJorunal.WriteToConfig(config, uniqueId.toString());
    }
    // End of Serialization

    public boolean AcceptQuest(Quest quest){
       // TODO: Refactor Method and cleanup code
        if(questJorunal.ContainsQuestName(quest.GetQuestName())){
           AllimoreLogger.LogInfo(ConfigWrapper.INFO_CANNOT_ACCEPT_QUEST_SAME_NAME, GetOnlinePlayer());
           return false;
       }
       if(quest.GetCurrentObjective() instanceof KillObjective){
           KillObjective objective = (KillObjective) quest.GetCurrentObjective();
           if(entityLocker.IsTypeLocked(objective.GetEntityType())){
               AllimoreLogger.LogInfo(ConfigWrapper.INFO_CANNOT_ACCEPT_QUEST_SAME_ENTITY, GetOnlinePlayer());
               return false;
           }
       }

       if( !(questJorunal.AddQuestToJournal(quest)) ){
           AllimoreLogger.LogInfo(ConfigWrapper.INFO_CANNOT_ACCEPT_QUEST_NO_FREE_SLOTS, GetOnlinePlayer());
           return false;
       }

       quest.notificationService.PlayStartNotification();
        if(quest.GetCurrentObjective() instanceof KillObjective){
            KillObjective objective = (KillObjective) quest.GetCurrentObjective();
            entityLocker.LockType(objective.GetEntityType());
        }
        return true;
    }

    public void AbandonQuest(Quest quest){
        questJorunal.RemoveQuestFromJournal(quest);
        quest.GetCurrentObjective().Disable();
        quest.notificationService.PlayAbandonNotification();
    }
    public void AbandonQuest(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return;
        AbandonQuest(quest);
    }

    public boolean TryCompleteQuestObjective(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return false;
        return TryCompleteQuestObjective(quest);
    }
    public boolean TryCompleteQuestObjective(Quest quest){
        boolean successful = quest.TryCompleteCurrentObjective();
        if(successful) {
            CompleteQuest(quest);
        }else{
            AllimoreLogger.LogInfo( ConfigWrapper.INFO_CANNOT_COMPLETE_QUEST, GetOnlinePlayer());
        }
        return successful;
    }

    public void CompleteQuest(Quest quest){
        quest.notificationService.PlayCompleteNotification();
        questJorunal.RemoveQuestFromJournal(quest);
        quest.GetCurrentObjective().Disable();
        // TODO: Inject Economy
        Allimorequest.GetInstance().GetEconomy().depositPlayer(offlinePlayer, quest.GetQuestRewardMoney());
        quest.notificationService.DisplayPayoutInChat(GetOnlinePlayer(), quest.GetQuestRewardMoney());
    }
    public void CompleteQuest(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return;
        CompleteQuest(quest);
    }

    // Pure Journal Wrappers
    public void SendQuestsToChat(){
        if(questJorunal.TrySendQuestsToChat()){
           AllimoreLogger.LogInfo(ConfigWrapper.INFO_EMPTY_QUEST_JOURNAL, GetOnlinePlayer());
        }
    }
    public void SendQuestStatusToChat(String name){
        Quest quest = questJorunal.Find(name);
        if(quest == null) return;
        quest.notificationService.DisplayQuestStatusInChat(GetOnlinePlayer());
    }
    public boolean ContainsQuestName(String name){
        return questJorunal.ContainsQuestName(name);
    }

    // GUI Wrapper
    public void OpenJournalGUI(){
        questJournalGUI.OpenGUI(GetOnlinePlayer());
    }
    public void OpenBoardGUI(){
        questBoardGUI.OpenGUI(GetOnlinePlayer());
    }

    // Getters and Setters
    public ArrayList<String> GetQuestNames(){
        return questJorunal.GetQuestNames();
    }
    public EntityLock GetEntityLocker(){
        return entityLocker;
    }
    public Quest[] GetQuests(){
        return questJorunal.GetQuests();
    }
    public OfflinePlayer GetPlayer(){
        return offlinePlayer;
    }
    public Player GetOnlinePlayer(){
        if(offlinePlayer.isOnline()){
            return Allimorequest.GetInstance().getServer().getPlayer(offlinePlayer.getUniqueId());
        }else{
            AllimoreLogger.LogError("Player is not currently online!");
            return null;
        }
    }
}
