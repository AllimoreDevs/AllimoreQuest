package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorequest.Quest;

public class GoToObjective extends Objective {
    private static float COMPLETION_RANGE = 10f;

    private Location targetLocaiton;

    public GoToObjective(String name, Quest quest, Location targetLocaiton) {
        super(name, quest);
        this.targetLocaiton = targetLocaiton;
    }
    public GoToObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        targetLocaiton = (Location)config.get(path + "Location");
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String section){
        super.WriteToConfig(config, section);
        config.set(section + "Location", targetLocaiton);
    }


    @Override
    public boolean IsComplete() {
        return quest.GetOnlinePlayer().getLocation().distance(targetLocaiton) <= COMPLETION_RANGE;
    }

    @Override
    public String GetProgress() {
        return "Distance: " + quest.GetOnlinePlayer().getLocation().distance(targetLocaiton);
    }

    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.GO_TO;
    }

    @Override
    public void Disable() {}


}
