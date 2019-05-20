package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.configuration.ConfigurationSection;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Objectives.KillObjective;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Quest;

import java.util.Set;

public class QuestFlairGenerator {
    private String[] keys = new String[]{
            "QUEST_NAME", "QUEST_GIVER", "MATERIAL", "TARGET_AMOUNT", "TARGET_TYPE"
    };
    private String[] values = new String[5];
    private CustomConfig questFlairFile;

    public QuestFlairGenerator(){
        questFlairFile = new CustomConfig("Quests.yml", Allimorequest.INSTANCE.getDataFolder().getPath(), Allimorequest.INSTANCE);
    }

    public String[] ReadRandomKillSummary(Quest quest, PlayerQuestData playerData){
        ConfigurationSection generalKillSection = (ConfigurationSection) questFlairFile.GetConfig().get("Kill.General");
        return GetQuestStrings(quest, generalKillSection, playerData);
    }

    private String[] GetQuestStrings(Quest quest, ConfigurationSection configurationSection, PlayerQuestData playerData) {
        Set<String> keys = configurationSection.getKeys(false);
        String[] keysArray = new String[keys.size()];
        keys.toArray(keysArray);

        String[] questFlair;
        String questName;
        int runningTotal = 0;
        while(true){
            questFlair = ReadRandomEntry(quest, configurationSection, keysArray);
            questName = questFlair[0];
            if(playerData.ContainsQuestName(questName)){
                if(runningTotal > 10) return null;
                runningTotal ++;
            }else{
                return questFlair;
            }
        }
    }

    private String[] ReadRandomEntry(Quest quest, ConfigurationSection configurationSection, String[] keysArray) {
        int rand = RandomUtils.getRandomNumberInRange(0, keysArray.length-1);
        String rawQuestSummary = configurationSection.getString(keysArray[rand]);
        return new String[]{ keysArray[rand], ParseKillQuestSummary(rawQuestSummary, keysArray[rand], quest) };
    }

    private void ReadBaseValues (Quest quest, String questName){
        values[0] = questName;
        values[1] = quest.GetQuestGiverName();

    }
    private String ParseKillQuestSummary(String string, String questName, Quest quest){
        ReadBaseValues(quest, questName);

        KillObjective objective = (KillObjective) quest.GetCurrentObjective();
        values[3] = Integer.toString(objective.GetTargetAmount());
        values[4] = StringUtils.formatEnumString(objective.GetEntityType().name() + "s");

        return org.apache.commons.lang.StringUtils.replaceEach(string, keys, values);
    }
}