package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import taurasi.marc.allimorequest.Quest;

public class KillNearObjective extends KillObjective {
    private Location questCenterLocation;
    private int range;

    // Construct New
    public KillNearObjective(String name, Quest quest, EntityType targetType, int targetAmount, Location questCenterLocation, int range) {
        super(name, quest, targetType, targetAmount);
        this.range = range;
        this.questCenterLocation = questCenterLocation;
    }
    // Serialization
    // Re-Construct from Config
    public KillNearObjective(FileConfiguration config, String path, String name, Quest quest){
        super(config, path, name, quest);
        this.range = config.getInt(path + "Range");
        this.questCenterLocation = (Location)config.get(path + "Location");
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String section){
        super.WriteToConfig(config, section);
        config.set(section + "Range", range);
        config.set(section + "Location", questCenterLocation);
    }
    // End of Serialization

    @Override
    public void OnEntityDeathEvent(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;

        if ( (event.getEntity().getKiller().equals(quest.GetPlayer()) ) &&
                (event.getEntity().getType() == targetType) &&
                (event.getEntity().getLocation().distance(questCenterLocation) > range)
        ){
            killedAmount++;
        }
        if(IsComplete()){
            quest.CompleteQuest();
        }
    }

    // Getters and Setters
    public Location GetLocation(){
        return questCenterLocation;
    }
    public int GetRange(){
        return range;
    }

    @Override
    public ObjectiveType GetType(){
        return ObjectiveType.KILL_NEAR;
    }
}
