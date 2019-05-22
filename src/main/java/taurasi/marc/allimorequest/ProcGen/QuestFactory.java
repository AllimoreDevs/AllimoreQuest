package taurasi.marc.allimorequest.ProcGen;

import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.Quest;

import java.util.ArrayList;

public class QuestFactory {
    public QuestFlairGenerator flairGenerator;
    private KillQuestFactory killQuestFactory;
    private CollectQuestFactory collectQuestFactory;

    private CustomConfig questGiverNamesConfig;

    public QuestFactory(){
        flairGenerator = new QuestFlairGenerator();
        killQuestFactory = new KillQuestFactory(this);
        collectQuestFactory = new CollectQuestFactory(this);
        questGiverNamesConfig = new CustomConfig("QuestGiverNames.yml", Allimorequest.INSTANCE.getDataFolder().getPath(), Allimorequest.INSTANCE);
    }

    public Quest GenerateQuest(PlayerProfession profession, PlayerQuestData playerData, DifficultyTier difficultyTier){
        switch(profession){
            case MINER:
                return collectQuestFactory.GenerateMinerQuest(playerData, difficultyTier);
            case EXCAVATOR:
                return collectQuestFactory.GenerateExcavtorQuest(playerData, difficultyTier);
            case WOODCUTTER:
                return collectQuestFactory.GenerateWoodcutterQuest(playerData, difficultyTier);
            case FARMER:
                return collectQuestFactory.GenerateFarmerQuest(playerData, difficultyTier);
            case SENTINEL:
                return killQuestFactory.GenerateKillQuest(playerData, difficultyTier);
        }
        return null;
    }
    public Quest GenerateQuest(PlayerProfession profession, PlayerQuestData playerData){
        return GenerateQuest(profession, playerData, GetRandomDifficulty());
    }

    public DifficultyTier GetRandomDifficulty() {
        int randomValue = RandomUtils.getRandomNumberInRange(0, 100);
        int runningTotal = 0;
        DifficultyTier[] difficultyTiers = Allimorequest.DIFFICULTY_MANAGER.GetDifficultyTiersArray();

        for(DifficultyTier tier : difficultyTiers){
            runningTotal += tier.appearanceChance;
            if(randomValue <= runningTotal){
                return tier;
            }
        }
        AllimoreLogger.LogError("Failed to generate random Tier.");
        return null;
    }
    public QuestGiver GenerateQuestGiver() {
        boolean isMale = Math.random() > .5;
        String path = (isMale) ? "Male" : "Female";
        ArrayList<String> names = (ArrayList<String>) questGiverNamesConfig.GetConfig().getStringList(path);
        String name = names.get(RandomUtils.getRandomNumberInRange(0, names.size() - 1));
        return new QuestGiver(name, isMale);
    }
}
