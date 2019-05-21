package taurasi.marc.allimorequest.ProcGen;

import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.FuzzyCollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.KillObjective;
import taurasi.marc.allimorequest.Quest;

public class QuestParser {
    private String[] keys = new String[]{
            "QUEST_NAME", "QUEST_GIVER", "MATERIAL", "TARGET_AMOUNT", "TARGET_TYPE"
    };
    private String[] values = new String[5];

    public String ParseQuestSummary(String rawSummary, String questName, Quest quest) {
        String parsedString;

        switch(quest.GetCurrentObjective().GetType()){
            case KILL:
                parsedString = ParseKillQuestSummary(rawSummary, questName, quest);
                break;
            case COLLECT:
                parsedString = ParseCollectQuestSummary(rawSummary, questName, quest);
                break;
            case COLLECT_FUZZY:
                parsedString = ParseFuzzyQuestSummary(rawSummary, questName, quest);
                break;
            default:
                parsedString = "PARSING ERROR";

        }
        return parsedString;
    }

    private void ReadBaseValues (Quest quest, String questName){
        values[0] = questName;
        values[1] = quest.GetQuestGiverName();

    }
    private String ParseCollectQuestSummary(String string, String questName, Quest quest){
        ReadBaseValues(quest, questName);

        CollectMaterialObjective objective = (CollectMaterialObjective) quest.GetCurrentObjective();
        values[2] = StringUtils.formatEnumString(objective.GetMaterial().name());
        values[3] = Integer.toString(objective.GetTargetAmount());

        return org.apache.commons.lang.StringUtils.replaceEach(string, keys, values);
    }
    private String ParseFuzzyQuestSummary(String string, String questName, Quest quest){
        ReadBaseValues(quest, questName);

        FuzzyCollectMaterialObjective objective = (FuzzyCollectMaterialObjective) quest.GetCurrentObjective();
        values[3] = Integer.toString(objective.GetTargetAmount());

        return org.apache.commons.lang.StringUtils.replaceEach(string, keys, values);
    }
    private String ParseKillQuestSummary(String string, String questName, Quest quest){
        ReadBaseValues(quest, questName);

        KillObjective objective = (KillObjective) quest.GetCurrentObjective();
        values[3] = Integer.toString(objective.GetTargetAmount());
        values[4] = StringUtils.formatEnumString(objective.GetEntityType().name() + "s");

        return org.apache.commons.lang.StringUtils.replaceEach(string, keys, values);
    }
}
