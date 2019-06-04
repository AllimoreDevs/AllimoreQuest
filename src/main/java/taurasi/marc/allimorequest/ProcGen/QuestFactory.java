package taurasi.marc.allimorequest.ProcGen;

import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.Professions.ProfessionMaterials;
import taurasi.marc.allimorequest.Quest;

import java.util.ArrayList;

public class QuestFactory {
    public QuestFlairGenerator flairGenerator;
    private KillQuestFactory killQuestFactory;
    private CollectQuestFactory collectQuestFactory;
    private DifficultyManager difficultyManager;

    private CustomConfig questGiverNamesConfig;

    public QuestFactory(ProfessionMaterials professionMaterials, DifficultyManager difficultyManager){
        flairGenerator = new QuestFlairGenerator();
        killQuestFactory = new KillQuestFactory(this);
        collectQuestFactory = new CollectQuestFactory(this, professionMaterials);
        questGiverNamesConfig = new CustomConfig("QuestGiverNames.yml", Allimorequest.GetInstance().getDataFolder().getPath(), Allimorequest.GetInstance());
        this.difficultyManager = difficultyManager;
    }

    public Quest GenerateQuest(PlayerProfession profession, PlayerQuestData playerData, QuestCollection questCollection, DifficultyTier difficultyTier) throws Exception {
        switch (profession) {
            case MINER:
                return collectQuestFactory.GenerateMinerQuest(playerData, difficultyTier, questCollection);
            case EXCAVATOR:
                return collectQuestFactory.GenerateExcavtorQuest(playerData, difficultyTier, questCollection);
            case WOODCUTTER:
                return collectQuestFactory.GenerateWoodcutterQuest(playerData, difficultyTier, questCollection);
            case FARMER:
                return collectQuestFactory.GenerateFarmerQuest(playerData, difficultyTier, questCollection);
            case SENTINEL:
                return killQuestFactory.GenerateKillQuest(playerData, difficultyTier, questCollection);
            case FISHER:
                return collectQuestFactory.GenerateFisherQuest(playerData, difficultyTier, questCollection);
        }
        return null;
    }
    public Quest GenerateQuest(PlayerProfession profession, PlayerQuestData playerData, DifficultyTier difficultyTier) throws Exception {
        return GenerateQuest(profession, playerData, playerData, difficultyTier);
    }
    public Quest GenerateQuest(PlayerProfession profession, PlayerQuestData playerData) throws Exception {
        return GenerateQuest(profession, playerData, playerData, GetRandomDifficulty());
    }
    public Quest GenerateQuest(PlayerQuestData playerData) throws Exception {
        return GenerateQuest(GetRandomProfession(), playerData, playerData, GetRandomDifficulty());
    }
    public Quest GenerateQuest(PlayerQuestData playerData, QuestCollection questCollection) throws Exception {
        return GenerateQuest(GetRandomProfession(), playerData, questCollection, GetRandomDifficulty());
    }

    public DifficultyTier GetRandomDifficulty() {
        int randomValue = RandomUtils.getRandomNumberInRange(0, 100);
        int runningTotal = 0;
        DifficultyTier[] difficultyTiers = difficultyManager.GetDifficultyTiersArray();

        for(DifficultyTier tier : difficultyTiers){
            runningTotal += tier.appearanceChance;
            if(randomValue <= runningTotal){
                return tier;
            }
        }
        AllimoreLogger.LogError("Failed to generate random Tier.");
        return null;
    }
    public PlayerProfession GetRandomProfession(){
        PlayerProfession[] professions = PlayerProfession.values();
        return professions[RandomUtils.getRandomNumberInRange(0, professions.length-1)];
    }
    public QuestGiver GenerateQuestGiver() {
        boolean isMale = Math.random() > .5;
        String path = (isMale) ? "Male" : "Female";
        ArrayList<String> names = (ArrayList<String>) questGiverNamesConfig.GetConfig().getStringList(path);
        String name = names.get(RandomUtils.getRandomNumberInRange(0, names.size() - 1));
        return new QuestGiver(name, isMale);
    }
    public double GetRandomPayout(DifficultyTier difficultyTier){
        return difficultyTier.GetRange("PayoutRange").GetRandomInRange();
    }
}
