package taurasi.marc.allimorequest.Commands.SubCommands;

import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import taurasi.marc.allimorequest.Commands.AllimorePermissionCommand;
import taurasi.marc.allimorequest.Commands.CommandManager;

public class WriteDataCommand extends AllimorePermissionCommand {
    public WriteDataCommand(String name, CommandManager cmdManager, String permissionString) throws Exception {
        super(name, cmdManager, permissionString);
    }
    public WriteDataCommand(String name, CommandManager commandManager, Permission permission) throws Exception {
        super(name, commandManager, permission);
    }

    @Override
    public boolean Execute(Player player, String[] args) {
        cmdManager.playerDataIndex.WriteData();
        return true;
    }
    @Override
    public void ConstructArgumentTypes() {}
}
