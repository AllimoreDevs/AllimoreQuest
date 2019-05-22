package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.MappedRange;
import taurasi.marc.allimorecore.Range;

import java.util.HashMap;
import java.util.Set;

public class DifficultyTier {
    public HashMap<String, Range> ranges;
    public String name;
    public int appearanceChance;

    public DifficultyTier(String name, int appearanceChance){
        this.name = name;
        this.appearanceChance = appearanceChance;
    }
    public DifficultyTier(FileConfiguration config, String path, String name){
        path = path + name + ".";
        ConfigurationSection section = config.getConfigurationSection(path);
        this.name = name;
        this.appearanceChance = section.getInt("Appearance Chance");
        ReadRangesFromConfig(config, path);
    }

    private void ReadRangesFromConfig(FileConfiguration config, String path){
        path = path + "Ranges.";
        ConfigurationSection section = config.getConfigurationSection(path);
        Set<String> keys = section.getKeys(false);
        ranges = new HashMap<>(keys.size());

        for(String key : keys) {
            ranges.put(key.toUpperCase(), new MappedRange(config, path + key + "."));
        }
    }

    public Range GetRange(String key){
        return ranges.get(key.toUpperCase());
    }
}
