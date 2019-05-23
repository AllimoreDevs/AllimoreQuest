package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Commands.AllimoreCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;

public class QuestBoardCommand extends AllimoreCommand {
    public QuestBoardCommand(String name, CommandManager cmdManager) {
        super(name, cmdManager);
    }

    @Override
    public boolean Run(Player player, String[] args) {
        cmdManager.playerDataIndex.GetPlayerData(player).OpenBoardGUI();
        return true;
    }
    @Override
    public void ConstructArgumentTypes() {}
}
