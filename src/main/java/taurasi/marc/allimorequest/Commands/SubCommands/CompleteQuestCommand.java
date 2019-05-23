package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Commands.AllimoreCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;
import taurasi.marc.allimorequest.Commands.TabCompleteOptions;
import taurasi.marc.allimorequest.Config.ConfigWrapper;

public class CompleteQuestCommand extends AllimoreCommand {
    public CompleteQuestCommand(String name, CommandManager cmdManager) {
        super(name, cmdManager);
    }

    @Override
    public boolean Run(Player player, String[] args) {
        if(args.length < 2){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_COMPLETE_QUEST_USAGE , player);
            return false;
        }
        String questName = cmdManager.ConstructStringFromArgs(args, 1);
        return cmdManager.playerDataIndex.GetPlayerData(player).TryCompleteQuestObjective(questName);
    }

    @Override
    public void ConstructArgumentTypes() {
        arguementTabTypes = new TabCompleteOptions[]{
                TabCompleteOptions.QUEST_NAMES
        };
    }
}
