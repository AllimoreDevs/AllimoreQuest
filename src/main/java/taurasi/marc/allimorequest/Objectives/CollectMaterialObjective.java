package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.ConversionUtils;
import taurasi.marc.allimorecore.InventoryUtils;
import taurasi.marc.allimorequest.Quest;

public class CollectMaterialObjective extends Objective {
    private Material itemMaterial;
    private int amount;

    public CollectMaterialObjective(String name, Quest quest, Material itemMaterial, int amount){
        super(name, quest);
        this.itemMaterial = itemMaterial;
        this.amount = amount;
    }
    public CollectMaterialObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        itemMaterial = ConversionUtils.ConvertStringToMaterial(config.getString(path + "Material"));
        amount = config.getInt(path + "Amount");
    }

    @Override
    public void WriteToConfig(FileConfiguration config, String section){
        super.WriteToConfig(config, section);
        config.set(section + "Material", itemMaterial.name());
        config.set(section + "Amount", amount);
    }

    private void Complete(){
        InventoryUtils.RemoveQuantityOfMaterial(quest.GetOnlinePlayer().getInventory(), itemMaterial, amount);
    }

    @Override
    public boolean IsComplete() {
        if(quest.GetOnlinePlayer().getInventory().contains(itemMaterial, amount)){
            Complete();
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String GetProgress() {
        int amountGathered = InventoryUtils.GetAmountOfMaterialFromInventory(itemMaterial, quest.GetOnlinePlayer().getInventory());
        return String.format("%s/%s", Math.min(amountGathered, amount), amount);
    }

    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.COLLECT;
    }
}
