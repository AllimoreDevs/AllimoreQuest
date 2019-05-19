package taurasi.marc.allimorequest;
import org.bukkit.command.Command;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.Objectives.KillObjective;

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
            return RunGenerateQuestCommand(player);
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
    private boolean RunGenerateQuestCommand(Player player){
        //Quest dummyQuest = new Quest("Red", "Collection", "Red has asked you to collect 10 leads for his stable in Volair.", player);
        //CollectMaterialObjective objective = new CollectMaterialObjective("Collect 10 leads", dummyQuest, Material.LEAD, 10);
        //dummyQuest.SetCurrentObjective(objective);

        Quest dummyQuest = new Quest("Tod", "Extermination", "Tod has asked you to kill zombies around the world.", Allimorequest.PLAYER_DATA.GetPlayerData(player));
        KillObjective objective = new KillObjective("Kill 3 Zombies", dummyQuest, EntityType.ZOMBIE, 3);
        dummyQuest.SetCurrentObjective(objective);

        Allimorequest.PLAYER_DATA.GetPlayerData(player).AcceptQuest(dummyQuest);
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
