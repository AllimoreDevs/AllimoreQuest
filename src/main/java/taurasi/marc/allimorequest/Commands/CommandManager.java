package taurasi.marc.allimorequest.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.PlayerDataIndex;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.ProcGen.DifficultyTier;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.Quest;

public class CommandManager implements CommandExecutor {
    private PlayerDataIndex playerDataIndex;

    public CommandManager(PlayerDataIndex playerDataIndex){
        this.playerDataIndex = playerDataIndex;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( sender instanceof Player){
            Player player = (Player) sender;

            if(IsCommand(command, "QuestJournal")){
                return RunQuestJournalCommand(player);
            }
            if(IsCommand(command, "QuestBoard")){
                return RunQuestBoardCommand(player);
            }
            if(IsCommand(command, "AbandonQuest")){
                return RunAbandonQuestCommand(player, args);
            }
            if(IsCommand(command, "GenerateQuest")){
                return RunGenerateQuestCommand(player, args);
            }
            if(IsCommand(command, "CompleteQuest")){
                return RunCompleteQuestCommand(player, args);
            }
            if(IsCommand(command, "WriteData")){
                return RunWriteDataCommand();
            }
            if(IsCommand(command, "QuestStatus")){
                return RunQuestStatusCommand(player, args);
            }
            if(IsCommand(command, "ForceCompleteQuest")){
                return RunForceCompleteQuestCommand(player, args);
            }

        }else{
            AllimoreLogger.LogInfo("Only players can run quest commands!");
        }

        return false;
    }

    private boolean RunQuestBoardCommand(Player player) {
        playerDataIndex.GetPlayerData(player).OpenBoardGUI();
        return true;
    }
    private boolean RunQuestJournalCommand(Player player){
        playerDataIndex.GetPlayerData(player).OpenJournalGUI();
        return true;
    }
    private boolean RunAbandonQuestCommand(Player player, String[] args){
        if(args.length < 1){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_ABANDON_QUEST_USAGE , player);
            return false;
        }
        String questName = ConstructStringFromArgs(args);
        playerDataIndex.GetPlayerData(player).AbandonQuest(questName);
        return true;
    }
    private boolean RunGenerateQuestCommand(Player player, String[] args){
        PlayerQuestData playerData = playerDataIndex.GetPlayerData(player);

        DifficultyTier difficultyTier = null;
        PlayerProfession profession = null;

        if(args.length > 0){
            if( !(PlayerProfession.Contains(args[0])) ){
                AllimoreLogger.LogInfo("Could not find specified player profession!", player);
                return false;
            }
            profession = PlayerProfession.valueOf(args[0].toUpperCase());
        }
        else{
            AllimoreLogger.LogInfo("You must provide a player profession!", player);
            return false;
        }
        if(args.length > 1){
            difficultyTier = Allimorequest.DIFFICULTY_MANAGER.GetDifficultyTier(args[1]);
            if(difficultyTier == null){
                AllimoreLogger.LogInfo("Could not find specified Difficulty Tier!", player);
                return false;
            }
        }

        Quest quest = (difficultyTier == null) ?
                Allimorequest.QUEST_FACTORY.GenerateQuest(profession, playerData) :
                Allimorequest.QUEST_FACTORY.GenerateQuest(profession, playerData, difficultyTier);

        if(quest == null){
            return false;
        }

        playerData.AcceptQuest(quest);
        return true;
    }
    private boolean RunCompleteQuestCommand(Player player, String[] args){
        if(args.length < 1){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_COMPLETE_QUEST_USAGE , player);
            return false;
        }
        String questName = ConstructStringFromArgs(args);
        return playerDataIndex.GetPlayerData(player).TryCompleteQuestObjective(questName);
    }
    private boolean RunForceCompleteQuestCommand(Player player, String[] args){
        if(args.length < 1){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_FORCE_COMPLETE_QUEST_USAGE , player);
            return false;
        }
        PlayerQuestData qp = playerDataIndex.GetPlayerData(player);
        String questName = ConstructStringFromArgs(args);
        qp.CompleteQuest(questName);
        return true;
    }
    private boolean RunQuestStatusCommand(Player player, String[] args){
        if(args.length == 0){
            playerDataIndex.GetPlayerData(player).SendQuestsToChat();
            return true;
        }

        String questName = ConstructStringFromArgs(args);
        playerDataIndex.GetPlayerData(player).SendQuestStatusToChat(questName);
        return true;
    }
    private boolean RunWriteDataCommand(){
        playerDataIndex.WriteData();
        return true;
    }

    private String ConstructStringFromArgs(String[] args, int skipArgs){
        StringBuilder sb = new StringBuilder();
        for(int i = skipArgs; i < args.length; i++){
            sb.append(args[i]);
            // If statement prevents a space form being appended at the end
            if(i < args.length - 1) sb.append(' ');
        }

        return sb.toString();
    }
    private String ConstructStringFromArgs(String[] args){
        return ConstructStringFromArgs(args, 0);
    }

    private boolean IsCommand(Command command,String name){
        return command.getName().equalsIgnoreCase(name);
    }
}
