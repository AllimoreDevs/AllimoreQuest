package taurasi.marc.allimorequest.Objectives;

import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.FuzzyMaterial;
import taurasi.marc.allimorecore.InventoryUtils;
import taurasi.marc.allimorequest.Quest;

public class FuzzyCollectMaterialObjective extends Objective {
    private FuzzyMaterial materials;
    private int targetAmount;

    // Construct new
    public FuzzyCollectMaterialObjective(String name, Quest quest, FuzzyMaterial materials, int targetAmount) {
        super(name, quest);
        this.materials = materials;
        this.targetAmount = targetAmount;
    }
    // Serialization
    // Re-construct from config
    public FuzzyCollectMaterialObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        materials = new FuzzyMaterial(config, path + "Materials.");
        targetAmount = config.getInt(path + "Target Amount");
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String path){
        super.WriteToConfig(config, path);
        materials.WriteToConfig(config, path + "Materials.");
        config.set(path + "Target Amount", targetAmount);
    }
    // End of Serialization

    @Override
    public boolean IsComplete() {
        int amountGatehered = InventoryUtils.GetAmountOfFuzzyMaterialFromInventory(materials, quest.GetOnlinePlayer().getInventory());
        if(amountGatehered >= targetAmount){
            SubmitQuestItems();
            return true;
        }
        return false;
    }

    private void SubmitQuestItems(){
        InventoryUtils.RemoveQuantityOfFuzzyMaterial( quest.GetOnlinePlayer().getInventory(), materials, targetAmount );
    }

    @Override
    public void Disable() {}

    // Getters and Setters
    public FuzzyMaterial GetFuzzyMaterial(){
        return materials;
    }
    public int GetTargetAmount(){
        return targetAmount;
    }
    @Override
    public String GetProgress() {
        int amountGatehered = InventoryUtils.GetAmountOfFuzzyMaterialFromInventory(materials, quest.GetOnlinePlayer().getInventory());
        return String.format("%s/%s", Math.min(amountGatehered, targetAmount), targetAmount);
    }
    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.COLLECT_FUZZY;
    }
}
