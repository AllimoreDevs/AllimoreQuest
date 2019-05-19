package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import taurasi.marc.allimorequest.Quest;

public class KillNearObjective extends KillObjective {
    private Location questCenter;
    private int range;

    public KillNearObjective(String name, Quest quest, EntityType targetType, int targetAmount, Location questCenter, int range) {
        super(name, quest, targetType, targetAmount);
        this.range = range;
        this.questCenter = questCenter;
    }
    public KillNearObjective(FileConfiguration config, String path, String name, Quest quest){
        super(config, path, name, quest);
        this.range = config.getInt(path + "Range");
        this.questCenter = (Location)config.get(path + "Location");
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String section){
        super.WriteToConfig(config, section);
        config.set(section + "Range", range);
        config.set(section + "Location", questCenter);
    }

    @Override
    public ObjectiveType GetType(){
        return ObjectiveType.KILL_NEAR;
    }

    @Override
    public void Notify(EntityDeathEvent event){
        if(event.getEntity().getKiller() == null) return;

        if ( (event.getEntity().getKiller().equals(quest.GetPlayer()) ) &&
                (event.getEntity().getType() == targetType) &&
                (event.getEntity().getLocation().distance(questCenter) > range)
        ){
            killedAmount++;
        }
        if(IsComplete()){
            quest.CompleteQuest();
        }
    }
}
