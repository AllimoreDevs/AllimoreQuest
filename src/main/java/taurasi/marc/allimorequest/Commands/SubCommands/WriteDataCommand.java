package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Commands.AllimoreCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;

public class WriteDataCommand extends AllimoreCommand {
    public WriteDataCommand(String name, CommandManager cmdManager) {
        super(name, cmdManager);
    }

    @Override
    public boolean Run(Player player, String[] args) {
        cmdManager.playerDataIndex.WriteData();
        return true;
    }
    @Override
    public void ConstructArgumentTypes() {}
}
