package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.Material;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.Professions.Excavator;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Quest;

public class CollectQuestFactory {
    private QuestFactory questFactory;

    public CollectQuestFactory(QuestFactory questFactory){
        this.questFactory = questFactory;
    }

    public Quest GenerateCollectQuest(PlayerProfession profession, DifficultyTier difficulty, PlayerQuestData playerData){
        switch(profession){
            case MINER:
//                return GenerateMinerQuest(playerData);
                break;
            case EXCAVATOR:
                return GenerateExcavtorQuest(playerData, difficulty);
            case WOODCUTTER:
//                return GenerateWoodcutterQuest(playerData);
                break;
        }
        return null;
    }
    public Quest GenerateCollectQuest(PlayerProfession profession, PlayerQuestData playerData){
        DifficultyTier difficulty = questFactory.GetRandomDifficulty();
        return GenerateCollectQuest(profession, difficulty, playerData);
    }

    public Quest GenerateExcavtorQuest(PlayerQuestData playerData, DifficultyTier difficulty){
        String questGiverName = questFactory.GenerateGiverName();


        Quest quest = new Quest(questGiverName, playerData);
        CollectMaterialObjective objective = GenerateExcavatorCollectMaterialObjective(quest, difficulty);

        quest.SetCurrentObjective(objective);
        String[] questFlair = questFactory.flairGenerator.ReadRandomExcavatorSummary(quest, playerData);
        quest.SetQuestName(questFlair[0]);
        quest.SetQuestSummary(questFlair[1]);

        return quest;
    }
    private CollectMaterialObjective GenerateExcavatorCollectMaterialObjective(Quest quest, DifficultyTier difficulty){
        Material targetMaterial = GetRandomExcavatorMaterial();
        int targetAmount = GetRandomAmountOfBulkBlocks(difficulty);

        String name = String.format("Collect %s", StringUtils.formatEnumString(targetMaterial.name())) ;
        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }

    public int GetRandomAmountOfBulkBlocks(DifficultyTier tier){
        Range range;
        switch (tier){
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
    public Material GetRandomExcavatorMaterial(){
        Material[] excavatorMaterials = Excavator.excavatorMaterials;
        return excavatorMaterials[RandomUtils.getRandomNumberInRange(0, excavatorMaterials.length - 1)];
    }

}
