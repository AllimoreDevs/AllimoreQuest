package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.Material;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.FuzzyCollectMaterialObjective;
import taurasi.marc.allimorequest.Professions.ExcavatorQuestMaterials;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Professions.FarmerQuestMaterials;
import taurasi.marc.allimorequest.Professions.MinerQuestMaterials;
import taurasi.marc.allimorequest.Professions.WoodcutterQuestMaterials;
import taurasi.marc.allimorequest.Quest;

public class CollectQuestFactory {
    private QuestFactory questFactory;

    public CollectQuestFactory(QuestFactory questFactory){
        this.questFactory = questFactory;
    }

    public Quest GenerateExcavtorQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        CollectMaterialObjective objective = GenExcavatorObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetExcavatorQuestFlair(quest, playerData, questGiver);

        return quest;
    }
    private CollectMaterialObjective GenExcavatorObjective(Quest quest, DifficultyTier difficultyTier){
        Material targetMaterial = GetRandomExcavatorMaterial();
        int targetAmount = GetRandomAmountOfBulkBlocks(difficultyTier);

        String name = String.format("Collect %s", StringUtils.formatEnumString(targetMaterial.name())) ;
        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }

    public Quest GenerateWoodcutterQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        FuzzyCollectMaterialObjective objective = GenWoodcutterObjective(quest ,difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetWoodcutterQuestFlair(quest, playerData, questGiver);
        return quest;
    }
    public FuzzyCollectMaterialObjective GenWoodcutterObjective(Quest quest, DifficultyTier difficultyTier){
        String name = "Collects Logs";
        int targetAmount = GetRandomAmountOfLogs(difficultyTier);
        return new FuzzyCollectMaterialObjective(name, quest, WoodcutterQuestMaterials.logs, targetAmount);
    }

    public Quest GenerateMinerQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        CollectMaterialObjective objective = GenMinerObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetMinerQuestFlair(quest, playerData, questGiver);
        return quest;
    }
    public CollectMaterialObjective GenMinerObjective(Quest quest, DifficultyTier difficultyTier){
        // Coal quests have a 4/10 chance of appearing
        boolean isCoalQuest = RandomUtils.getRandomNumberInRange(1, 10) > 6;

        int targetAmount = (isCoalQuest) ? GetRandomAmountOfCoal(difficultyTier) : GetRandomAmountOfBulkBlocks(difficultyTier);
        Material targetMaterial = (isCoalQuest) ? Material.COAL : GetRandomMinerStoneMaterial();

        String name = (isCoalQuest) ? "Collect Coal" : String.format("Collect %s", StringUtils.formatEnumString(targetMaterial.name()) );
        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }

    public Quest GenerateFarmerQuest(PlayerQuestData playerData, DifficultyTier difficultyTier){
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        CollectMaterialObjective objective = GenFarmerObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetFarmerQuestFlair(quest, playerData, questGiver);
        return quest;
    }
    public CollectMaterialObjective GenFarmerObjective(Quest quest, DifficultyTier difficultyTier){
        Material targetMaterial = GetRandomFarmerMaterial();
        String name = String.format("Collect " + QuestParser.BruteForcePlural(targetMaterial));
        int targetAmount = GetRandomAmountOfCrops(difficultyTier);

        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }

    private int GetRandomAmountOfCrops(DifficultyTier difficultyTier) {
        return difficultyTier.GetRange("CollectFarmable").GetRandomInRange();
    }
    public int GetRandomAmountOfBulkBlocks(DifficultyTier difficultyTier){
        return difficultyTier.GetRange("CollectBulkBlocksRange").GetRandomInRange();
    }
    public int GetRandomAmountOfLogs(DifficultyTier difficultyTier){
        return difficultyTier.GetRange("CollectLogs").GetRandomInRange();
    }
    public int GetRandomAmountOfCoal(DifficultyTier difficultyTier){
        return difficultyTier.GetRange("CollectOre").GetRandomInRange();
    }

    public Material GetRandomFarmerMaterial(){
        Material[] materials = FarmerQuestMaterials.materials;
        return materials[RandomUtils.getRandomNumberInRange(0, materials.length - 1)];
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
