package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Commands.AllimoreCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;
import taurasi.marc.allimorequest.Commands.TabCompleteOptions;

public class QuestStatusCommand extends AllimoreCommand {
    public QuestStatusCommand(String name, CommandManager cmdManager) {
        super(name, cmdManager);
    }

    @Override
    public boolean Run(Player player, String[] args) {
        if(args.length < 1){
            cmdManager.playerDataIndex.GetPlayerData(player).SendQuestsToChat();
            return true;
        }

        String questName = cmdManager.ConstructStringFromArgs(args, 1);
        cmdManager.playerDataIndex.GetPlayerData(player).SendQuestStatusToChat(questName);
        return true;
    }

    @Override
    public void ConstructArgumentTypes() {
        arguementTabTypes = new TabCompleteOptions[]{
                TabCompleteOptions.QUEST_NAMES
        };
    }

}
