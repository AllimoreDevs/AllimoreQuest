package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.InventoryUtils;
import taurasi.marc.allimorequest.Quest;

public class CollectMaterialObjective extends Objective {
    private Material material;
    private int targetAmount;

    public CollectMaterialObjective(String name, Quest quest, Material material, int targetAmount){
        super(name, quest);
        this.material = material;
        this.targetAmount = targetAmount;
    }
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

    private void Complete(){
        InventoryUtils.RemoveQuantityOfMaterial(quest.GetOnlinePlayer().getInventory(), material, targetAmount);
    }

    @Override
    public boolean IsComplete() {
        if(quest.GetOnlinePlayer().getInventory().contains(material, targetAmount)){
            Complete();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String GetProgress() {
        int amountGathered = InventoryUtils.GetAmountOfMaterialFromInventory(material, quest.GetOnlinePlayer().getInventory());
        return String.format("%s/%s", Math.min(amountGathered, targetAmount), targetAmount);
    }
    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.COLLECT;
    }

    @Override
    public void Disable() {}

    public int GetTargetAmount(){
        return targetAmount;
    }
    public Material GetMaterial(){
        return material;
    }
}
