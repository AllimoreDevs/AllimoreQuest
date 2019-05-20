package taurasi.marc.allimorequest;

import org.bukkit.entity.EntityType;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Objectives.KillObjective;

import java.util.ArrayList;

public class EntityLock {
    private ArrayList<EntityType> lockedTypes;

    public EntityLock(){
        lockedTypes = new ArrayList<>();
    }

    public void LockTypesFromQuests(QuestJournal questJournal){
        lockedTypes = new ArrayList<>();

        Quest[] quests = questJournal.GetQuests();
        for (Quest quest : quests) {
            if (quest == null) continue;
            if (quest.GetCurrentObjective() instanceof KillObjective) {
                KillObjective objective = (KillObjective) quest.GetCurrentObjective();
                LockType(objective.GetEntityType());
            }
        }
    }
    public boolean IsTypeLocked(EntityType type){
        return lockedTypes.contains(type);
    }
    public void LockType(EntityType type){
        if(lockedTypes.contains(type)){
            AllimoreLogger.LogError("Cannot lock type that is already locked!");
            return;
        }
        lockedTypes.add(type);
    }
    public void UnlockType(EntityType type){
        if(!lockedTypes.contains(type)){
            AllimoreLogger.LogError("Cannot Unlock type, type not found in locked types!");
            return;
        }
        lockedTypes.remove(type);
    }
}
