package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.Material;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Objectives.CollectMaterialObjective;
import taurasi.marc.allimorequest.Objectives.FuzzyCollectMaterialObjective;
import taurasi.marc.allimorequest.Professions.*;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Quest;

public class CollectQuestFactory {
    private QuestFactory questFactory;
    private ProfessionMaterials professionMaterialsIndex;

    public CollectQuestFactory(QuestFactory questFactory, ProfessionMaterials professionMaterialsIndex){
        this.questFactory = questFactory;
        this.professionMaterialsIndex = professionMaterialsIndex;
    }

    public Quest GenerateExcavtorQuest(PlayerQuestData playerData, DifficultyTier difficultyTier, QuestCollection questCollection) throws Exception {
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        CollectMaterialObjective objective = GenExcavatorObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetExcavatorQuestFlair(quest, questCollection, questGiver);

        return quest;
    }
    public Quest GenerateWoodcutterQuest(PlayerQuestData playerData, DifficultyTier difficultyTier, QuestCollection questCollection) throws Exception {
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        FuzzyCollectMaterialObjective objective = GenWoodcutterObjective(quest ,difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetWoodcutterQuestFlair(quest, questCollection, questGiver);
        return quest;
    }
    public Quest GenerateMinerQuest(PlayerQuestData playerData, DifficultyTier difficultyTier, QuestCollection questCollection) throws Exception {
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        CollectMaterialObjective objective = GenMinerObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetMinerQuestFlair(quest, questCollection, questGiver);
        return quest;
    }
    public Quest GenerateFarmerQuest(PlayerQuestData playerData, DifficultyTier difficultyTier, QuestCollection questCollection) throws Exception {
        QuestGiver questGiver = questFactory.GenerateQuestGiver();

        Quest quest = new Quest(questGiver.name, playerData);
        CollectMaterialObjective objective = GenFarmerObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetFarmerQuestFlair(quest, questCollection, questGiver);
        return quest;
    }
    public Quest GenerateFisherQuest(PlayerQuestData playerData, DifficultyTier difficultyTier, QuestCollection questCollection) throws Exception {
        QuestGiver questGiver = questFactory.GenerateQuestGiver();


        Quest quest = new Quest(questGiver.name, playerData);
        CollectMaterialObjective objective = GenFisherObjective(quest, difficultyTier);
        quest.SetCurrentObjective(objective);

        questFactory.flairGenerator.SetFisherQuestFlair(quest, questCollection, questGiver);
        return quest;
    }

    private CollectMaterialObjective GenExcavatorObjective(Quest quest, DifficultyTier difficultyTier){
        Material targetMaterial = GetRandomProfessionMaterial(PlayerProfession.EXCAVATOR);;
        int targetAmount = GetRandomAmountOfBulkBlocks(difficultyTier);

        String name = String.format("Collect %s", StringUtils.formatEnumString(targetMaterial.name())) ;
        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }
    public FuzzyCollectMaterialObjective GenWoodcutterObjective(Quest quest, DifficultyTier difficultyTier){
        String name = "Collects Logs";
        int targetAmount = GetRandomAmountOfLogs(difficultyTier);
        return new FuzzyCollectMaterialObjective(name, quest, professionMaterialsIndex.logs, targetAmount);
    }
    public CollectMaterialObjective GenMinerObjective(Quest quest, DifficultyTier difficultyTier){
        // Coal quests have a 4/10 chance of appearing
        boolean isCoalQuest = RandomUtils.getRandomNumberInRange(1, 10) > 6;

        int targetAmount = (isCoalQuest) ? GetRandomAmountOfCoal(difficultyTier) : GetRandomAmountOfBulkBlocks(difficultyTier);
        Material targetMaterial = (isCoalQuest) ? Material.COAL : GetRandomProfessionMaterial(PlayerProfession.MINER);

        String name = (isCoalQuest) ? "Collect Coal" : String.format("Collect %s", StringUtils.formatEnumString(targetMaterial.name()) );
        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }
    public CollectMaterialObjective GenFarmerObjective(Quest quest, DifficultyTier difficultyTier){
        Material targetMaterial = GetRandomProfessionMaterial(PlayerProfession.FARMER);
        String name = String.format("Collect " + QuestParser.BruteForcePlural(targetMaterial));
        int targetAmount = GetRandomAmountOfCrops(difficultyTier);

        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }
    public CollectMaterialObjective GenFisherObjective(Quest quest, DifficultyTier difficultyTier){
        Material targetMaterial = GetRandomProfessionMaterial(PlayerProfession.FISHER);
        String name = "Collect " + StringUtils.formatEnumString(targetMaterial.name());
        int targetAmount = GetRandomAmountOfFish(difficultyTier);

        return new CollectMaterialObjective(name, quest, targetMaterial, targetAmount);
    }

    private int GetRandomAmountOfFish(DifficultyTier difficultyTier) {
        return difficultyTier.GetRange("CollectFish").GetRandomInRange();
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

    public Material GetRandomProfessionMaterial(PlayerProfession profession){
        Material[] materials = professionMaterialsIndex.GetProfessionMaterials(profession);
        return materials[RandomUtils.getRandomNumberInRange(0, materials.length)];
    }
}
