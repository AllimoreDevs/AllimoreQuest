package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import taurasi.marc.allimorequest.Commands.AllimorePermissionCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;

public class QuestBoardCommand extends AllimorePermissionCommand {
    public QuestBoardCommand(String name, CommandManager cmdManager, String permissionString) throws Exception {
        super(name, cmdManager, permissionString);
    }
    public QuestBoardCommand(String name, CommandManager commandManager, Permission permission) throws Exception {
        super(name, commandManager, permission);
    }

    @Override
    public boolean Execute(Player player, String[] args) {
        cmdManager.playerDataIndex.GetPlayerData(player).OpenBoardGUI();
        return true;
    }
    @Override
    public void ConstructArgumentTypes() {}
}
