package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Quest;
import java.util.Set;

public class QuestFlairGenerator {
    private CustomConfig questFlairFile;
    private QuestParser questParser;

    public QuestFlairGenerator(){
        questFlairFile = new CustomConfig("QuestGenerationStrings.yml", Allimorequest.INSTANCE.getDataFolder().getPath(), Allimorequest.INSTANCE);
        questParser = new QuestParser();
    }

    public void SetKillQuestFlair(Quest quest, PlayerQuestData playerData){
        SetRandomSummary(quest, playerData, "Kill.General");
    }
    public void SetExcavatorQuestFlair(Quest quest, PlayerQuestData playerData){
        CollectMaterialObjective objective = (CollectMaterialObjective)quest.GetCurrentObjective();
        String path = String.format("Collect.Profession.Excavator.%s", objective.GetMaterial().name().toLowerCase());

        SetRandomSummary(quest, playerData, path);
    }
    public void SetWoodcutterQuestFlair(Quest quest, PlayerQuestData playerData){
        SetRandomSummary(quest, playerData, "Collect.Profession.Woodcutter.General");
    }
    public void SetMinerQuestFlair(Quest quest, PlayerQuestData playerData){
        CollectMaterialObjective objective = (CollectMaterialObjective) quest.GetCurrentObjective();
        String path = "Collect.Profession.Miner.";
        if(objective.GetMaterial().equals(Material.COAL)){
            path = path + "Coal";
        }else{
            path = path + "Bulk Stone";
        }
        SetRandomSummary(quest, playerData, path);
    }

    public void SetRandomSummary(Quest quest, PlayerQuestData playerQuestData, String sectionPath){
        ConfigurationSection section = questFlairFile.GetConfig().getConfigurationSection(sectionPath);

        String[] keys = GetKeysArray(section);
        // Asset was here
        String questName = TryGetUnusedQuestName(playerQuestData, keys);
        // Assert was here
        String questSummary = section.getString(questName);
        questSummary = questParser.ParseQuestSummary(questSummary, questName, quest);

        quest.SetQuestName(questName);
        quest.SetQuestSummary(questSummary);
    }
    private String[] GetKeysArray(ConfigurationSection configurationSection) {
        Set<String> keys = configurationSection.getKeys(false);
        String[] keysArray = new String[keys.size()];
        keys.toArray(keysArray);
        return keysArray;
    }
    private String TryGetUnusedQuestName(PlayerQuestData playerQuestData, String[] keys){
        String questName;

        for(int iterations = 0; iterations < keys.length; iterations++){
            int rand = RandomUtils.getRandomNumberInRange(0, keys.length - 1);
            if(keys[rand] == null) continue;

            questName = keys[rand];
            if(playerQuestData.ContainsQuestName(questName)){
                keys[rand] = null;
            }else{
                return questName;
            }
        }
        AllimoreLogger.LogError("Could not find unused quest name!");
        return null;
    }
}
