package taurasi.marc.allimorequest.Commands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.ProcGen.DifficultyTier;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.Quest;

public class CommandManager implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if ( sender instanceof Player){
            Player player = (Player) sender;

            if(IsCommand(command, "QuestJournal")){
                return RunQuestJournalCommand(player);
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

    private boolean RunQuestJournalCommand(Player player){
        Allimorequest.PLAYER_DATA.GetPlayerData(player).OpenJournalGUI();
        return true;
    }
    private boolean RunAbandonQuestCommand(Player player, String[] args){
        if(args.length != 1){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_ABANDON_QUEST_USAGE , player);
            return false;
        }
        Allimorequest.PLAYER_DATA.GetPlayerData(player).AbandonQuest(args[0]);
        return true;
    }
    private boolean RunGenerateQuestCommand(Player player, String[] args){
        PlayerQuestData playerData = Allimorequest.PLAYER_DATA.GetPlayerData(player);

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
        if(args.length != 1){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_COMPLETE_QUEST_USAGE , player);
            return false;
        }
        return Allimorequest.PLAYER_DATA.GetPlayerData(player).TryCompleteQuestObjective(args[0]);
    }
    private boolean RunForceCompleteQuestCommand(Player player, String[] args){
        if(args.length != 1){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_FORCE_COMPLETE_QUEST_USAGE , player);
            return false;
        }
        PlayerQuestData qp = Allimorequest.PLAYER_DATA.GetPlayerData(player);
        qp.CompleteQuest(args[0]);
        return true;
    }
    private boolean RunQuestStatusCommand(Player player, String[] args){
        if(args.length != 1){
            Allimorequest.PLAYER_DATA.GetPlayerData(player).SendQuestsToChat();
            return true;
        }

        Allimorequest.PLAYER_DATA.GetPlayerData(player).SendQuestStatusToChat(args[0]);
        return true;
    }
    private boolean RunWriteDataCommand(){
        Allimorequest.PLAYER_DATA.WriteData();
        return true;
    }

    private boolean IsCommand(Command command,String name){
        return command.getName().equalsIgnoreCase(name);
    }
}
