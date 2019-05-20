package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorequest.Quest;

public class GoToObjective extends Objective {
    private static float COMPLETION_RANGE = 10f;
    private Location targetLocation;

    // Construct New
    public GoToObjective(String name, Quest quest, Location targetLocation) {
        super(name, quest);
        this.targetLocation = targetLocation;
    }
    // Serialization
    // Re-construct from Config
    public GoToObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        targetLocation = (Location)config.get(path + "Location");
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String section){
        super.WriteToConfig(config, section);
        config.set(section + "Location", targetLocation);
    }
    // End of Serialization

    @Override
    public boolean IsComplete() {
        return quest.GetOnlinePlayer().getLocation().distance(targetLocation) <= COMPLETION_RANGE;
    }

    @Override
    public void Disable() {}

    // Getters and Setters
    public Location GetLocation(){
        return targetLocation;
    }

    @Override
    public String GetProgress() {
        return "Distance: " + quest.GetOnlinePlayer().getLocation().distance(targetLocation);
    }
    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.GO_TO;
    }
}
