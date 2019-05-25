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
        questFlairFile = new CustomConfig("QuestGenerationStrings.yml", Allimorequest.GetInstance().getDataFolder().getPath(), Allimorequest.GetInstance());
        questParser = new QuestParser();
    }

    public void SetKillQuestFlair(Quest quest, QuestCollection questCollection, QuestGiver questGiver) throws Exception {
        SetRandomSummary(quest, questCollection, "Kill.General", questGiver);
    }
    public void SetExcavatorQuestFlair(Quest quest, QuestCollection questCollection, QuestGiver questGiver) throws Exception {
        CollectMaterialObjective objective = (CollectMaterialObjective)quest.GetCurrentObjective();
        String path = String.format("Collect.Profession.Excavator.%s", objective.GetMaterial().name().toLowerCase());

        SetRandomSummary(quest, questCollection, path, questGiver);
    }
    public void SetWoodcutterQuestFlair(Quest quest, QuestCollection questCollection, QuestGiver questGiver) throws Exception {
        SetRandomSummary(quest, questCollection, "Collect.Profession.Woodcutter.General", questGiver);
    }
    public void SetMinerQuestFlair(Quest quest, QuestCollection questCollection, QuestGiver questGiver) throws Exception {
        CollectMaterialObjective objective = (CollectMaterialObjective) quest.GetCurrentObjective();
        String path = "Collect.Profession.Miner.";
        if(objective.GetMaterial().equals(Material.COAL)){
            path = path + "Coal";
        }else{
            path = path + "Bulk Stone";
        }
        SetRandomSummary(quest, questCollection, path, questGiver);
    }
    public void SetFarmerQuestFlair(Quest quest, QuestCollection questCollection, QuestGiver questGiver) throws Exception {
        CollectMaterialObjective objective = (CollectMaterialObjective) quest.GetCurrentObjective();
        Material targetMaterial = objective.GetMaterial();

        String path = "Collect.Profession.Farmer.";

        if(targetMaterial == Material.WHEAT){
            if(Math.random() < .66)
                path = path + "Wheat";
        }else if(targetMaterial == Material.MELON_SLICE){
            if(Math.random() < .66)
                path = path + "Melon";
        }else{
            path = path + "General";
        }

        SetRandomSummary(quest, questCollection, path, questGiver);
    }
    public void SetFisherQuestFlair(Quest quest, QuestCollection questCollection, QuestGiver questGiver) throws Exception {
        SetRandomSummary(quest, questCollection, "Collect.Profession.Fisher.General", questGiver);
    }

    public void SetRandomSummary(Quest quest, QuestCollection questCollection, String sectionPath, QuestGiver questGiver) throws Exception {
        ConfigurationSection section = questFlairFile.GetConfig().getConfigurationSection(sectionPath);

        String[] keys = GetKeysArray(section);
        // Asset was here
        String questName = TryGetUnusedQuestName(questCollection, keys);

        if(questName == null){
            throw new Exception("Could not get unused questname!");
        }

        String questSummary = section.getString(questName);
        questSummary = questParser.ParseQuestSummary(questSummary, questName, quest, questGiver);

        quest.SetQuestName(questName);
        quest.SetQuestSummary(questSummary);
    }
    private String[] GetKeysArray(ConfigurationSection configurationSection) {
        Set<String> keys = configurationSection.getKeys(false);
        String[] keysArray = new String[keys.size()];
        keys.toArray(keysArray);
        return keysArray;
    }
    private String TryGetUnusedQuestName(QuestCollection questCollection, String[] keys){
        String questName;

        for(int iterations = 0; iterations < keys.length; iterations++){
            int rand = RandomUtils.getRandomNumberInRange(0, keys.length - 1);
            if(keys[rand] == null) continue;

            questName = keys[rand];
            if(questCollection.ContainsQuestName(questName)){
                keys[rand] = null;
            }else{
                return questName;
            }
        }
        AllimoreLogger.LogError("Could not find unused quest name!");
        return null;
    }
}
