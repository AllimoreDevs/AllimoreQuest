package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.InventoryUtils;
import taurasi.marc.allimorequest.Quest;

public class CollectMaterialObjective extends Objective {
    private Material material;
    private int targetAmount;

    // Construct New
    public CollectMaterialObjective(String name, Quest quest, Material material, int targetAmount){
        super(name, quest);
        this.material = material;
        this.targetAmount = targetAmount;
    }

    // Serialization
    // Re-Construct From Config
    public CollectMaterialObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        material = Material.getMaterial(config.getString(path + "Material"), false);
        targetAmount = config.getInt(path + "Amount");
    }

    @Override
    public void WriteToConfig(FileConfiguration config, String section){
        super.WriteToConfig(config, section);
        config.set(section + "Material", material.name());
        config.set(section + "Amount", targetAmount);
    }
    // End of Serialization

    @Override
    public boolean IsComplete() {
        AllimoreLogger.LogInfo("Target Amount " + targetAmount + ", Target Material " + material.name());
        if(quest.GetOnlinePlayer().getInventory().contains(material, targetAmount)){
            SubmitQuestItems();
            return true;
        }else{
            return false;
        }
    }
    private void SubmitQuestItems(){
        InventoryUtils.RemoveQuantityOfMaterial(quest.GetOnlinePlayer().getInventory(), material, targetAmount);
    }

    @Override
    public void Disable() {}

    // Getters and Setters
    public int GetTargetAmount(){
        return targetAmount;
    }
    public Material GetMaterial(){
        return material;
    }

    @Override
    public String GetProgress() {
        AllimoreLogger.LogInfo(quest.GetOnlinePlayer().getDisplayName());
        AllimoreLogger.LogInfo(quest.GetOnlinePlayer().getInventory().getItem(0).getType().name());

        int amountGathered = InventoryUtils.GetAmountOfMaterialFromInventory(material, quest.GetOnlinePlayer().getInventory());
        return String.format("%s/%s", Math.min(amountGathered, targetAmount), targetAmount);
    }
    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.COLLECT;
    }
}
