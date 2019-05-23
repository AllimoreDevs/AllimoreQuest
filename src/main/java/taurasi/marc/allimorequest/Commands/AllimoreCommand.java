package taurasi.marc.allimorequest.Commands;

import org.bukkit.entity.Player;

public abstract class AllimoreCommand {
    public String name;
    protected CommandManager cmdManager;
    protected TabCompleteOptions[] arguementTabTypes;

    public AllimoreCommand(String name, CommandManager cmdManager){
        this.name = name;
        this.cmdManager = cmdManager;
        ConstructArgumentTypes();
    }

    public abstract boolean Run(Player player, String[] args);
    public abstract void ConstructArgumentTypes();

    public boolean IsCommand(String string){
        return string.equalsIgnoreCase(name);
    }
}
