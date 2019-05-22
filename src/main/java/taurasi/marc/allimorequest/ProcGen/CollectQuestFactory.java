package taurasi.marc.allimorequest.ProcGen;

import com.sun.org.apache.xerces.internal.xs.StringList;
import org.bukkit.Material;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.Range;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.FuzzyCollectMaterialObjective;
import taurasi.marc.allimorequest.Professions.ExcavatorQuestMaterials;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Professions.MinerQuestMaterials;
import taurasi.marc.allimorequest.Professions.WoodcutterQuestMaterials;
import taurasi.marc.allimorequest.Quest;

public class CollectQuestFactory {
    private QuestFactory questFactory;

    public CollectQuestFactory(QuestFactory questFactory){
        this.questFactory = questFactory;
    }

    public Quest GenerateExcavtorQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        String questGiverName = questFactory.GenerateGiverName();

        Quest quest = new Quest(questGiverName, playerData);
        CollectMaterialObjective objective = GenExcavatorObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetExcavatorQuestFlair(quest, playerData);

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

        questFactory.flairGenerator.SetWoodcutterQuestFlair(quest, playerData);
        return quest;
    }
    public FuzzyCollectMaterialObjective GenWoodcutterObjective(Quest quest, DifficultyTier difficultyTier){
        String name = "Collects Logs";
        int targetAmount = GetRandomAmountOfLogs(difficultyTier);
        return new FuzzyCollectMaterialObjective(name, quest, WoodcutterQuestMaterials.logs, targetAmount);
    }

    public Quest GenerateMinerQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        String questGiverName = questFactory.GenerateGiverName();

        Quest quest = new Quest(questGiverName, playerData);
        CollectMaterialObjective objective = GenMinerObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetMinerQuestFlair(quest, playerData);
        return quest;
    }
    public CollectMaterialObjective GenMinerObjective(Quest quest, DifficultyTier difficultyTier){
        // Coal quests have a 4/10 chance of appearing
        boolean isCoalQuest = RandomUtils.getRandomNumberInRange(1, 10) > 6;

        int targetAmount = (isCoalQuest) ? GetRandomAmountOfCoal(difficultyTier) : GetRandomAmountOfBulkBlocks(difficultyTier);
        Material targetMaterial = (isCoalQuest) ? Material.COAL : GetRandomMinerStoneMaterial();

        String name = (isCoalQuest) ? "Collect Coal" : String.format("Collect %s", targetMaterial);
        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
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
    public int GetRandomAmountOfCoal(DifficultyTier difficultyTier){
        Range range;
        switch(difficultyTier){
            case NOVICE:
                range = DifficultyTier.NOVICE.collectOreAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case ADVENTURER:
                range = DifficultyTier.ADVENTURER.collectOreAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case LEGEND:
                range = DifficultyTier.LEGEND.collectOreAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
            case CHAMPION:
                range = DifficultyTier.CHAMPION.collectOreAmountRange;
                return RandomUtils.getRandomNumberInRange(range.min, range.max);
        }
        AllimoreLogger.LogError("Could not resolve difficulty type!");
        return 1;
    }
    public Material GetRandomExcavatorMaterial(){
        Material[] excavatorMaterials = ExcavatorQuestMaterials.materials;
        return excavatorMaterials[RandomUtils.getRandomNumberInRange(0, excavatorMaterials.length - 1)];
    }
    public Material GetRandomMinerStoneMaterial(){
        Material[] minerMaterials = MinerQuestMaterials.materials;
        return minerMaterials[RandomUtils.getRandomNumberInRange(0, minerMaterials.length - 1)];
    }
}