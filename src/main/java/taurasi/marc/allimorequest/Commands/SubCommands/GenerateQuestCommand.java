package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Commands.AllimoreCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;
import taurasi.marc.allimorequest.Commands.TabCompleteOptions;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.ProcGen.DifficultyTier;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.Quest;

public class GenerateQuestCommand extends AllimoreCommand {
    public GenerateQuestCommand(String name, CommandManager cmdManager) {
        super(name, cmdManager);
    }

    @Override
    public boolean Run(Player player, String[] args) {
        if( !(player.hasPermission("allimore.quest.command.generate-quest")) ) return false;

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
            difficultyTier = Allimorequest.DIFFICULTY_MANAGER.GetDifficultyTier(args[2]);
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
    @Override
    public void ConstructArgumentTypes() {
        arguementTabTypes = new TabCompleteOptions[]{
          TabCompleteOptions.PROFESSIONS, TabCompleteOptions.DIFFICULTY_TIERS
        };
    }
}
