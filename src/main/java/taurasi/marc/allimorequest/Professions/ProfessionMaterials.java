package taurasi.marc.allimorequest.Professions;

import org.bukkit.Material;
import taurasi.marc.allimorecore.FuzzyMaterial;

import java.util.HashMap;

public class ProfessionMaterials {
    public static FuzzyMaterial logs = new FuzzyMaterial(new Material[]{
            Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG,
            Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG
    });

    private static Material[] minerMaterials = new Material[]{
            Material.COBBLESTONE, Material.ANDESITE, Material.GRANITE, Material.DIORITE
    };

    private static Material[] fisherMaterials = new Material[]{
            Material.COD, Material.SALMON, Material.TROPICAL_FISH
    };

    private static Material[] farmerMaterials = new Material[]{
            Material.POTATO, Material.CARROT, Material.WHEAT, Material.BEETROOT, Material.SUGAR_CANE, Material.MELON_SLICE, Material.PUMPKIN
    };

    private static Material[] excavatorMaterials = new Material[]{
            Material.DIRT, Material.SAND, Material.GRAVEL
    };

    private HashMap<PlayerProfession, Material[]> professionMaterials;

    public ProfessionMaterials(){
        professionMaterials = new HashMap<>();
        professionMaterials.put(PlayerProfession.MINER, minerMaterials);
        professionMaterials.put(PlayerProfession.EXCAVATOR, excavatorMaterials);
        professionMaterials.put(PlayerProfession.FISHER, fisherMaterials);
        professionMaterials.put(PlayerProfession.FARMER, farmerMaterials);
    }

    public Material[] GetProfessionMaterials(PlayerProfession profession){
        return professionMaterials.get(profession);
    }
}
