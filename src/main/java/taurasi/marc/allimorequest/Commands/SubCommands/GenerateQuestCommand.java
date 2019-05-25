package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Commands.AllimorePermissionCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;
import taurasi.marc.allimorequest.Commands.TabCompleteOptions;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.ProcGen.DifficultyTier;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.Quest;

public class GenerateQuestCommand extends AllimorePermissionCommand {
    public GenerateQuestCommand(String name, CommandManager cmdManager, String permissionString) throws Exception {
        super(name, cmdManager, permissionString);
    }
    public GenerateQuestCommand(String name, CommandManager commandManager, Permission permission) throws Exception {
        super(name, commandManager, permission);
    }

    @Override
    public boolean Execute(Player player, String[] args) {
        PlayerQuestData playerData = cmdManager.playerDataIndex.GetPlayerData(player);

        DifficultyTier difficultyTier = null;
        PlayerProfession profession = null;

        if(args.length > 2){
            if( !(PlayerProfession.Contains(args[1])) ){
                AllimoreLogger.LogInfo("Could not find specified player profession!", player);
                return false;
            }
            profession = PlayerProfession.valueOf(args[1].toUpperCase());
        }
        else{
            AllimoreLogger.LogInfo("You must provide a player profession!", player);
            return false;
        }
        if(args.length > 3){
            difficultyTier = cmdManager.GetDifficultyManager().GetDifficultyTier(args[2]);
            if(difficultyTier == null){
                AllimoreLogger.LogInfo("Could not find specified Difficulty Tier!", player);
                return false;
            }
        }

        Quest quest = null;
        try {
            quest = (difficultyTier == null) ?
                    cmdManager.GetQuestFactory().GenerateQuest(profession, playerData) :
                    cmdManager.GetQuestFactory().GenerateQuest(profession, playerData, difficultyTier);
        } catch (Exception e) {
            AllimoreLogger.LogError("Quest Generation Failed!", player);
        }

        if(quest == null){
            return false;
        }

        playerData.AcceptQuest(quest);
        return true;
    }
    @Override
    public void ConstructArgumentTypes() {
        arguementTabTypes = new TabCompleteOptions[]{
          TabCompleteOptions.PROFESSIONS, TabCompleteOptions.DIFFICULTY_TIERS
        };
    }
}
