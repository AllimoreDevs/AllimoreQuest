package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Commands.AllimoreCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;

public class QuestJournalCommand extends AllimoreCommand {
    public QuestJournalCommand(String name, CommandManager cmdManager) {
        super(name, cmdManager);
    }

    @Override
    public boolean Run(Player player, String[] args) {
        cmdManager.playerDataIndex.GetPlayerData(player).OpenJournalGUI();
        return true;
    }
    @Override
    public void ConstructArgumentTypes() {}
}
