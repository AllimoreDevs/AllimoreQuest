package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.Material;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.FuzzyCollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.KillObjective;
import taurasi.marc.allimorequest.Quest;

public class QuestParser {
    private String[] keys = new String[]{
            "QUEST_NAME", "QUEST_GIVER", "PRONOUN_LOWER", "PRONOUN", "POSSESSIVE_LOWER", "POSSESSIVE", "TARGET_MATERIAL", "TARGET_AMOUNT", "TARGET_TYPE"
    };
    private String[] values = new String[keys.length];

    public String ParseQuestSummary(String rawSummary, String questName, Quest quest, QuestGiver questGiver) {
        String parsedString;

        switch(quest.GetCurrentObjective().GetType()){
            case KILL:
                parsedString = ParseKillQuestSummary(rawSummary, questName, quest, questGiver);
                break;
            case COLLECT:
                parsedString = ParseCollectQuestSummary(rawSummary, questName, quest, questGiver);
                break;
            case COLLECT_FUZZY:
                parsedString = ParseFuzzyQuestSummary(rawSummary, questName, quest, questGiver);
                break;
            default:
                parsedString = "PARSING ERROR";

        }
        return parsedString;
    }

    public static String BruteForcePlural(Material material){
        switch(material){
            case POTATO:
                return "Potatoes";
            case WHEAT:
                return "Wheat";
            case CHICKEN:
                return "Chicken";
        }
        return StringUtils.formatEnumString(material.name()) + "s";
    }

    private void ReadBaseValues (Quest quest, String questName, QuestGiver questGiver){
        values[0] = questName;
        values[1] = quest.GetQuestGiverName();

        values[2] = questGiver.GetPronoun().toLowerCase();
        values[3] = questGiver.GetPronoun();

        values[4] = questGiver.GetPossessivePronoun().toLowerCase();
        values[5] = questGiver.GetPossessivePronoun();

    }
    private String ParseCollectQuestSummary(String string, String questName, Quest quest, QuestGiver questGiver){
        ReadBaseValues(quest, questName, questGiver);

        CollectMaterialObjective objective = (CollectMaterialObjective) quest.GetCurrentObjective();
        values[6] = StringUtils.formatEnumString( BruteForcePlural(objective.GetMaterial()) );
        values[7] = Integer.toString(objective.GetTargetAmount());

        return org.apache.commons.lang.StringUtils.replaceEach(string, keys, values);
    }
    private String ParseFuzzyQuestSummary(String string, String questName, Quest quest, QuestGiver questGiver){
        ReadBaseValues(quest, questName, questGiver);

        FuzzyCollectMaterialObjective objective = (FuzzyCollectMaterialObjective) quest.GetCurrentObjective();
        values[7] = Integer.toString(objective.GetTargetAmount());

        return org.apache.commons.lang.StringUtils.replaceEach(string, keys, values);
    }
    private String ParseKillQuestSummary(String string, String questName, Quest quest, QuestGiver questGiver){
        ReadBaseValues(quest, questName, questGiver);

        KillObjective objective = (KillObjective) quest.GetCurrentObjective();
        values[7] = Integer.toString(objective.GetTargetAmount());
        values[8] = StringUtils.formatEnumString(objective.GetEntityType().name() + "s");

        return org.apache.commons.lang.StringUtils.replaceEach(string, keys, values);
    }
}
