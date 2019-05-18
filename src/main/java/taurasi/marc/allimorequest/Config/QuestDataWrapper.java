package taurasi.marc.allimorequest.Config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import taurasi.marc.allimorequest.Allimorequest;

import java.io.File;
import java.io.IOException;

public class QuestDataWrapper {
    private String dataFileName = "PlayerData.yml";
    private File dataFile;
    private FileConfiguration dataConfig;

    public QuestDataWrapper(){
        CreateDataFile();
    }

    private void CreateDataFile(){
        GetDataFile();
        GetConfigFromFile();
    }

    public void SaveConfig(){
        try{
            dataConfig.save(dataFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void SaveOverConfig(YamlConfiguration config){
        try {
            config.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void GetDataFile(){
        dataFile = new File(Allimorequest.INSTANCE.getDataFolder(), dataFileName);
        if(!dataFile.exists()){
            dataFile.getParentFile().mkdirs();
            Allimorequest.INSTANCE.saveResource(dataFileName, false);
        }
    }

    private void GetConfigFromFile(){
        dataConfig = new YamlConfiguration();
        try{
            dataConfig.load(dataFile);
        }catch (IOException | InvalidConfigurationException e){
            e.printStackTrace();
        }
    }

    public FileConfiguration GetConfig(){
        return dataConfig;
    }
}
