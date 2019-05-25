package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.configuration.ConfigurationSection;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorequest.Allimorequest;

import java.util.HashMap;
import java.util.Set;

public class DifficultyManager {
    private HashMap<String, DifficultyTier> difficultyTiers;
    private CustomConfig configFile;

    public DifficultyManager(){
        String configFileName = "DifficultyTiers.yml";
        configFile = new CustomConfig(configFileName, Allimorequest.GetInstance().getDataFolder().getPath(), Allimorequest.GetInstance());
        ReadFromConfig();
    }

    public void ReadFromConfig(){
        ConfigurationSection section = configFile.GetConfig().getConfigurationSection("Tiers.");
        Set<String> keys = section.getKeys(false);
        difficultyTiers = new HashMap<>(keys.size());
        for(String key : keys){
            difficultyTiers.put(key.toUpperCase(), new DifficultyTier(configFile.GetConfig(), "Tiers.", key));
        }
    }

    public DifficultyTier GetDifficultyTier(String name){
        return difficultyTiers.get(name.toUpperCase());
    }
    public DifficultyTier[] GetDifficultyTiersArray(){
        DifficultyTier[] difficultyTiers = new DifficultyTier[this.difficultyTiers.size()];
        return this.difficultyTiers.values().toArray(difficultyTiers);
    }
}
