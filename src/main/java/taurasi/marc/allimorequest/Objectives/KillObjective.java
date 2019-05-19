package taurasi.marc.allimorequest.Objectives;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDeathEvent;
import taurasi.marc.allimorecore.ConversionUtils;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Observers.EntityDeathObserver;
import taurasi.marc.allimorequest.Quest;

public class KillObjective extends Objective implements EntityDeathObserver {
    int targetAmount;
    int killedAmount;
    EntityType targetType;

    public KillObjective(String name, Quest quest, EntityType targetType, int targetAmount) {
        super(name, quest);
        this.targetType = targetType;
        this.targetAmount = targetAmount;
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    public KillObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        targetType = ConversionUtils.ConvertStringtoEntityType(config.getString(path + "Target Type"));
        targetAmount = config.getInt(path + "Target Amount");
        killedAmount = config.getInt(path + "Killed Amount");
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String section) {
        super.WriteToConfig(config, section);
        config.set(section + "Target Type", targetType.name());
        config.set(section + "Target Amount", targetAmount);
        config.set(section + "Killed Amount", killedAmount);
    }

    @Override
    public boolean IsComplete() {
        boolean complete = killedAmount >= targetAmount;
        if(complete) Allimorequest.EVENT_LISTENER.Unsubscribe(this);
        return complete;
    }

    @Override
    public String GetProgress() {
        return String.format("%s/%s", killedAmount, targetAmount);
    }

    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.KILL;
    }

    @Override
    public void Notify(EntityDeathEvent event) {
        if(event.getEntity().getKiller() == null) return;

        if ( (event.getEntity().getKiller().equals(quest.GetPlayer()) ) &&
                (event.getEntity().getType() == targetType) ){
            killedAmount++;
        }
        if(IsComplete()){
            quest.CompleteQuest();
        }
    }


}
