package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.Material;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.Range;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.FuzzyCollectMaterialObjective;
import taurasi.marc.allimorequest.Professions.ExcavatorQuestMaterials;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Professions.WoodcutterQuestMaterials;
import taurasi.marc.allimorequest.Quest;

public class CollectQuestFactory {
    private QuestFactory questFactory;

    public CollectQuestFactory(QuestFactory questFactory){
        this.questFactory = questFactory;
    }

    public Quest GenerateCollectQuest(PlayerProfession profession, PlayerQuestData playerData, DifficultyTier difficultyTier){
        switch(profession){
            case MINER:
//                return GenerateMinerQuest(playerData);
                break;
            case EXCAVATOR:
                return GenerateExcavtorQuest(playerData, difficultyTier);
            case WOODCUTTER:
                return GenerateWoodcutterQuest(playerData, difficultyTier);
        }
        return null;
    }
    public Quest GenerateCollectQuest(PlayerProfession profession, PlayerQuestData playerData){
        DifficultyTier difficulty = questFactory.GetRandomDifficulty();
        return GenerateCollectQuest(profession, playerData, difficulty);
    }

    public Quest GenerateExcavtorQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        String questGiverName = questFactory.GenerateGiverName();

        Quest quest = new Quest(questGiverName, playerData);
        CollectMaterialObjective objective = GenExcavatorObjective(quest, difficultyTier);

        quest.SetCurrentObjective(objective);
        String[] questFlair = questFactory.flairGenerator.ReadRandomExcavatorSummary(quest, playerData);
        quest.SetQuestName(questFlair[0]);
        quest.SetQuestSummary(questFlair[1]);

        return quest;
    }
    private CollectMaterialObjective GenExcavatorObjective(Quest quest, DifficultyTier difficultyTier){
        Material targetMaterial = GetRandomExcavatorMaterial();
        int targetAmount = GetRandomAmountOfBulkBlocks(difficultyTier);

        String name = String.format("Collect %s", StringUtils.formatEnumString(targetMaterial.name())) ;
        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }

    public Quest GenerateWoodcutterQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        String questGiverName = questFactory.GenerateGiverName();

        Quest quest = new Quest(questGiverName, playerData);
        FuzzyCollectMaterialObjective objective = GenWoodcutterObjective(quest ,difficultyTier);
        quest.SetCurrentObjective(objective);

        String[] questFlair = questFactory.flairGenerator.ReadRandomWoodcutterSummary(quest, playerData);
        quest.SetQuestName(questFlair[0]);
        quest.SetQuestSummary(questFlair[1]);

        return quest;
    }
    public FuzzyCollectMaterialObjective GenWoodcutterObjective(Quest quest, DifficultyTier difficultyTier){
        String name = String.format("Collects Logs");
        int targetAmount = GetRandomAmountOfLogs(difficultyTier);
        return new FuzzyCollectMaterialObjective(name, quest, WoodcutterQuestMaterials.logs, targetAmount);
    }

    public int GetRandomAmountOfBulkBlocks(DifficultyTier difficultyTier){
        Range range;
        switch (difficultyTier){
            case NOVICE:
                range = DifficultyTier.NOVICE.collectBulkBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case ADVENTURER:
                range = DifficultyTier.ADVENTURER.collectBulkBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case LEGEND:
                range = DifficultyTier.LEGEND.collectBulkBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case CHAMPION:
                range = DifficultyTier.CHAMPION.collectBulkBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
        }
        AllimoreLogger.LogError("Could not resolve difficulty type!");
        return 1;
    }
    public int GetRandomAmountOfLogs(DifficultyTier difficultyTier){
        Range range;
        switch(difficultyTier){
            case NOVICE:
                range = DifficultyTier.NOVICE.collectLogsBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case ADVENTURER:
                range = DifficultyTier.ADVENTURER.collectLogsBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case LEGEND:
                range = DifficultyTier.LEGEND.collectLogsBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case CHAMPION:
                range = DifficultyTier.CHAMPION.collectLogsBlockAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
        }
        AllimoreLogger.LogError("Could not resolve difficulty type!");
        return 1;
    }
    public Material GetRandomExcavatorMaterial(){
        Material[] excavatorMaterials = ExcavatorQuestMaterials.materials;
        return excavatorMaterials[RandomUtils.getRandomNumberInRange(0, excavatorMaterials.length - 1)];
    }

}
