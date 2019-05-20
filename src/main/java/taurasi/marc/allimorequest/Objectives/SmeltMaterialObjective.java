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

    // Construct New
    public SmeltMaterialObjective(String name, Quest quest, Material targetMaterial, int targetAmount) {
        super(name, quest);
        this.targetMaterial = targetMaterial;
        this.targetAmount  = targetAmount;
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    // Serialization
    // Re-Construct from Config
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
    // End of Serialization

    @Override
    public void OnFurnaceExtractEvent(FurnaceExtractEvent event) {
        if( !(event.getPlayer().equals(quest.GetPlayer()) || !(event.getExpToDrop() <= 0) )) return;
        if( !(event.getItemType().equals(targetMaterial)) ) return;
        completedAmount += event.getItemAmount();

        if(IsComplete()){
            quest.CompleteQuest();
        }
    }
    @Override
    public boolean IsComplete() {
        if(completedAmount >= targetAmount){
            Disable();
            return true;
        }
        return false;
    }

    @Override
    public void Disable() {
        Allimorequest.EVENT_LISTENER.Unsubscribe(this);
    }

    // Getters and Setters
    @Override
    public String GetProgress() {
        return String.format("%o/%o", completedAmount, targetAmount);
    }
    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.SMELT;
    }
}
