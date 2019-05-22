package taurasi.marc.allimorequest.Objectives;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.ConversionUtils;
import taurasi.marc.allimorecore.InventoryUtils;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Observers.CraftItemObserver;
import taurasi.marc.allimorequest.Quest;

public class CraftItemObjective extends Objective implements CraftItemObserver {
    private int targetAmount;
    private int craftedAmount;
    private Material targetMaterial;

    // Construct New
    public CraftItemObjective(String name, Quest quest, Material targetMaterial, int targetAmount) {
        super(name, quest);
        this.targetMaterial = targetMaterial;
        this.targetAmount = targetAmount;
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }

    // Serialization
    // Re-construct From File
    public CraftItemObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        targetMaterial =  Material.getMaterial(config.getString(path + "Material"), false);
        targetAmount = config.getInt(path + "Target Amount");
        craftedAmount = config.getInt(path + "Crafted Amount");
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String path){
        super.WriteToConfig(config, path);
        config.set(path + "Material", targetMaterial.name());
        config.set(path + "Target Amount", targetAmount);
        config.set(path + "Crafted Amount", craftedAmount);
    }
    // End of Serialization

    @Override
    public boolean IsComplete() {
        if(craftedAmount >= targetAmount){
            Disable();
        }else{
            return false;
        }
        Inventory playerInv = quest.GetOnlinePlayer().getInventory();
        if(playerInv.contains(targetMaterial, targetAmount)){
            SubmitItems(playerInv);
            return true;
        }
        return false;
    }

    private void SubmitItems(Inventory inv){
        InventoryUtils.RemoveQuantityOfMaterial(inv, targetMaterial, targetAmount);
    }

    @Override
    public void OnCraftItemEvent(CraftItemEvent event) {
        ItemStack result = event.getInventory().getResult();

        if( !(result.getType().equals(targetMaterial)) ) return;
        craftedAmount += result.getAmount();
        if( IsComplete()){
            quest.CompleteQuest();
        }
    }

    @Override
    public void Disable() {
        Allimorequest.EVENT_LISTENER.Unsubscribe(this);
    }

    // Getters and Setters
    public int GetTargetAmount(){
        return targetAmount;
    }
    public int GetCraftedAmount(){
        return craftedAmount;
    }
    public Material GetTargetMaterial(){
        return targetMaterial;
    }

    @Override
    public String GetProgress() {
        return String.format("%o/%o", Math.min(craftedAmount, targetAmount), targetAmount);
    }
    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.CRAFT;
    }
}
