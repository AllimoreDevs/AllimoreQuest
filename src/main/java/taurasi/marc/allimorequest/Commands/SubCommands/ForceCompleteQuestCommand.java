package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Commands.AllimoreCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;
import taurasi.marc.allimorequest.Commands.TabCompleteOptions;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.PlayerQuestData;

public class ForceCompleteQuestCommand extends AllimoreCommand {
    public ForceCompleteQuestCommand(String name, CommandManager cmdManager) {
        super(name, cmdManager);
    }

    @Override
    public boolean Run(Player player, String[] args) {
        if( !(player.hasPermission("allimore.quest.command.force-complete")) ) return false;

        if(args.length < 2){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_QUEST_NAME_PROVIDED, player);
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_FORCE_COMPLETE_QUEST_USAGE , player);
            return false;
        }
        PlayerQuestData qp = cmdManager.playerDataIndex.GetPlayerData(player);
        String questName = cmdManager.ConstructStringFromArgs(args, 1);
        qp.CompleteQuest(questName);
        return true;
    }

    @Override
    public void ConstructArgumentTypes() {
        arguementTabTypes = new TabCompleteOptions[]{
                TabCompleteOptions.QUEST_NAMES
        };
    }
}
