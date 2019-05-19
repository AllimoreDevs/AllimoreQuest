package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Observers.FurnaceExtractObserver;
import taurasi.marc.allimorequest.Quest;

public class SmeltMaterialObjective extends Objective implements FurnaceExtractObserver {
    private Material targetMaterial;
    private int targetAmount;
    private int completedAmount;

    public SmeltMaterialObjective(String name, Quest quest, Material targetMaterial, int targetAmount) {
        super(name, quest);
        this.targetMaterial = targetMaterial;
        this.targetAmount  = targetAmount;
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    public SmeltMaterialObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        targetMaterial = Material.getMaterial(config.getString(path + "Target Material"), false);
        targetAmount = config.getInt(path + "Target Amount");
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String path){
        super.WriteToConfig(config, path);
        config.set(path + "Target Material", targetMaterial);
        config.set(path + "Target Amount", targetAmount);
    }

    @Override
    public boolean IsComplete() {
        if(completedAmount >= targetAmount){
            Allimorequest.EVENT_LISTENER.Unsubscribe(this);
            return true;
        }
        return false;
    }

    @Override
    public String GetProgress() {
        return String.format("%o/%o", completedAmount, targetAmount);
    }

    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.SMELT;
    }

    @Override
    public void Notify(FurnaceExtractEvent event) {
        if( !(event.getPlayer().equals(quest.GetPlayer()) || !(event.getExpToDrop() <= 0) )) return;
        if( !(event.getItemType().equals(targetMaterial)) ) return;
        completedAmount += event.getItemAmount();

        if(IsComplete()){
            quest.CompleteQuest();
        }
    }
}
