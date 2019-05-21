package taurasi.marc.allimorequest.Professions;

import org.bukkit.Material;
import taurasi.marc.allimorecore.FuzzyMaterial;

public class WoodcutterQuestMaterials {
    public static FuzzyMaterial logs = new FuzzyMaterial(new Material[]{
            Material.ACACIA_LOG, Material.BIRCH_LOG, Material.DARK_OAK_LOG, Material.JUNGLE_LOG, Material.OAK_LOG, Material.SPRUCE_LOG,
            Material.STRIPPED_ACACIA_LOG, Material.STRIPPED_BIRCH_LOG, Material.STRIPPED_DARK_OAK_LOG, Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_OAK_LOG, Material.STRIPPED_SPRUCE_LOG
    });

    public static FuzzyMaterial planks = new FuzzyMaterial(new Material[]{
            Material.ACACIA_PLANKS, Material.BIRCH_PLANKS, Material.DARK_OAK_PLANKS, Material.JUNGLE_PLANKS, Material.OAK_PLANKS, Material.SPRUCE_PLANKS
    });
}
