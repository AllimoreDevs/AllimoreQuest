package taurasi.marc.allimorequest.Objectives;

import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Quest;

public abstract class Objective {
    protected String name;
    protected Quest quest;

    // Construct New
    public Objective(String name, Quest quest){
        this.name = name;
        this.quest = quest;
    }
    // Serialization
    // Re-Construct from Config
    public static Objective ReadObjective(FileConfiguration config, String path, Quest quest){
        String name = config.getString(path + "Name");
        ObjectiveType type = ObjectiveType.valueOf(config.getString(path + "Type"));
        switch (type){
            case KILL_PLAYER:
                return new KillPlayerObjective(config, path, name, quest);
            case KILL:
                return new KillObjective(config, path, name, quest);
            case KILL_NEAR:
                return new KillNearObjective(config, path, name, quest);
            case COLLECT:
                return new CollectMaterialObjective(config, path, name, quest);
            case GO_TO:
                return new GoToObjective(config, path, name, quest);
            case CRAFT:
                return new CraftItemObjective(config, path, name, quest);
            case SMELT:
                return  new SmeltMaterialObjective(config, path, name, quest);
        }
        AllimoreLogger.LogError("Could not resolve quest type!");
        return null;
    }
    public void WriteToConfig(FileConfiguration config, String section){
        config.set(section + "Name", name);
        config.set(section + "Type", GetType().name());
    }
    // End of Serialization

    public abstract boolean IsComplete();
    public abstract void Disable();

    // Getters and Setters
    public abstract String GetProgress();
    public abstract ObjectiveType GetType();
    public String GetName(){
        return name;
    }
}
