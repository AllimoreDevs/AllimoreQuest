package taurasi.marc.allimorequest.Utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Utils {
    public static boolean ItemNameMatch(ItemStack item, String nameString){
        return item.getItemMeta().getDisplayName().equals(nameString);
    }

    public static boolean ItemMaterialMatch(ItemStack item, Material material){
        return item.getType() == material;
    }

    public static boolean ItemMatch(ItemStack item, Material material, String nameString){
        return ItemMaterialMatch(item, material) && ItemNameMatch(item, nameString);
    }

    public static ItemStack InventoryContains(String nameString, Material material, int amount, Inventory inventory){
        for (int i = 0; i < inventory.getSize(); i++){
            ItemStack item =  inventory.getItem(i);
            if (item == null) continue;
            if( (ItemMatch(item, material, nameString)) && item.getAmount() >= amount){
                return item;
            }
        }
        return null;
    }

    public static int GetAmountOfMaterialFromInventory(Material material, Inventory inventory){
        int runningTotal = 0;

        for(int i = 0; i < inventory.getSize(); i++){
            ItemStack item = inventory.getItem(i);
            if(item == null) continue;
            if(item.getType() == material){
                runningTotal += item.getAmount();
            }
        }
        return runningTotal;
    }

    public static void RemoveQuantityOfMaterial(Inventory inventory, Material targetMaterial, int targetAmount){
        int removedAmount = 0;
        for(int i = 0; i < inventory.getSize(); i++){
            int remainingAmount = targetAmount - removedAmount;

            ItemStack item = inventory.getItem(i);
            if( item == null || !(item.getType() == targetMaterial) ) continue;

            if(item.getAmount() > remainingAmount ) {
                item.setAmount( item.getAmount() - remainingAmount );
                return;
            }

            if(item.getAmount() == remainingAmount) {
                inventory.removeItem(item);
                return;
            }

            if(item.getAmount() < remainingAmount){
                inventory.removeItem(item);
                removedAmount += item.getAmount();
            }
        }
    }

    public static ChatColor ConvertStringToColor(String string){
        return ChatColor.valueOf(string.toUpperCase());
    }
    public static Sound ConvertStringToSound(String string){
        return Sound.valueOf(string.toUpperCase());
    }
    public static EntityType ConvertStringtoEntityType(String string){
        return EntityType.valueOf(string.toUpperCase());
    }
    public static Material ConvertStringToMaterial(String string){
        return Material.valueOf(string.toUpperCase());
    }

    public static ArrayList<String> WrapLore(String string, int charactersPerLine, String appendString){
        ArrayList<String> loreStrings = new ArrayList<>();
        char[] chars = string.toCharArray();
        int charsOnLine = 0;

        for(int i = 0; i < chars.length; i++){
            if( ((chars.length - i) + charsOnLine <= charactersPerLine) ){
                loreStrings.add(appendString + string.substring(i - charsOnLine));
                break;
            }

            if( (chars[i] == ' ') && (charsOnLine >= charactersPerLine) ){
                loreStrings.add(appendString + string.substring(i - charsOnLine, i));
                charsOnLine = 0;
            }else{
                charsOnLine ++;
            }
        }

        return loreStrings;
    }
}
