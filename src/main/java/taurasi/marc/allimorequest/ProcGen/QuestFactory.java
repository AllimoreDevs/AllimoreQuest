package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.entity.EntityType;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.KillObjective;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Quest;

public class QuestFactory {
    private EntityType[] hostileTypes = new EntityType[]{
     EntityType.ZOMBIE, EntityType.CREEPER, EntityType.SKELETON, EntityType.SPIDER
    };
    private String[] killQuestNames = new String[]{
            "Extermination", "The Common Good", "Vanguard", "Taming the Wilderness", "Threat and Response", "Monster Hunter", "Monster Bounty", "Sentinel"
    };
    private QuestFlairGenerator flairGenerator;

    public QuestFactory(){
        flairGenerator = new QuestFlairGenerator();
    }

    public Quest GenerateKillQuest(PlayerQuestData playerData, DifficultyTier difficulty){
        String questGiver = GenerateGiverName();
        Quest quest = new Quest(questGiver, playerData);
        quest.SetCurrentObjective(GenerateKillObjective(quest, difficulty));

        String[] questFlair = flairGenerator.ReadRandomKillSummary(quest, playerData);
        quest.SetQuestName(questFlair[0]);
        quest.SetQuestSummary(questFlair[1]);

        return quest;
    }
    public Quest GenerateKillQuest(PlayerQuestData playerData){
        return GenerateKillQuest(playerData, GetRandomDifficulty());
    }

    private DifficultyTier GetRandomDifficulty() {
        int randomValue = RandomUtils.getRandomNumberInRange(0, 100);
        int runningTotal = 0;
        for(DifficultyTier tier : DifficultyTier.values()){
            runningTotal += tier.appearanceChance;
            if(randomValue <= runningTotal){
                return tier;
            }
        }
        AllimoreLogger.LogError("Failed to generate random Tier.");
        return DifficultyTier.NOVICE;
    }

    private KillObjective GenerateKillObjective(Quest quest, DifficultyTier difficulty){
        EntityType type = GetRandomHostileType();
        int amount = GetKillTargetAmountFromTier(difficulty);
        return new KillObjective(String.format("Kill %ss", StringUtils.formatEnumString(type.name())), quest, type, amount);
    }
    private int GetKillTargetAmountFromTier(DifficultyTier difficulty){
        return RandomUtils.getRandomNumberInRange(difficulty.killAmountRange.min, difficulty.killAmountRange.max);
    }
    private EntityType GetRandomHostileType(){
        return hostileTypes[RandomUtils.getRandomNumberInRange(0, hostileTypes.length-1)];
    }

    private String GenerateGiverName() {
        // TODO: Complete implementation
        return "The Guild";
    }
}
