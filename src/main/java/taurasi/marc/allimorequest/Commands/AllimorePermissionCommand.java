package taurasi.marc.allimorequest.Commands;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Config.ConfigWrapper;

public abstract class AllimorePermissionCommand extends AllimoreCommand {
    private Permission permission;

    public AllimorePermissionCommand(String name, CommandManager cmdManager, String permissionStirng) throws Exception {
        super(name, cmdManager);
        permission = Bukkit.getPluginManager().getPermission(permissionStirng);
        if(permission == null){
            throw new Exception("Failed to find permission " + permissionStirng);
        }
    }
    public AllimorePermissionCommand(String name, CommandManager cmdManager, Permission permission) throws Exception {
        super(name, cmdManager);
        if(permission == null){
            throw new Exception("Provided Permission Cannot be null!");
        }
        this.permission = permission;
    }

    @Override
    public boolean Run(Player player, String[] args){
        if( !HasPermission(player) ){
            AllimoreLogger.LogInfo(ConfigWrapper.INFO_NO_PERMISSIONS, player);
            return false;
        }
        return Execute(player, args);
    }

    public abstract boolean Execute(Player player, String[] args);

    boolean HasPermission(Player player){
        return player.hasPermission(permission);
    }
}
