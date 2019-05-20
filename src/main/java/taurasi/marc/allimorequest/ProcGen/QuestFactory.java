package taurasi.marc.allimorequest.ProcGen;

import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Professions.PlayerProfession;
import taurasi.marc.allimorequest.Quest;

public class QuestFactory {
    public QuestFlairGenerator flairGenerator;
    private KillQuestFactory killQuestFactory;
    private CollectQuestFactory collectQuestFactory;

    public QuestFactory(){
        flairGenerator = new QuestFlairGenerator();
        killQuestFactory = new KillQuestFactory(this);
        collectQuestFactory = new CollectQuestFactory(this);
    }

    public Quest GenerateKillQuest(PlayerQuestData playerData, DifficultyTier difficulty){
        return killQuestFactory.GenerateKillQuest(playerData, difficulty);
    }
    public Quest GenerateKillQuest(PlayerQuestData playerData){
        return killQuestFactory.GenerateKillQuest(playerData);
    }

    public Quest GenerateCollectQuest(PlayerProfession profession, DifficultyTier difficulty, PlayerQuestData playerData){
        return collectQuestFactory.GenerateCollectQuest(profession, difficulty, playerData);
    }
    public Quest GenerateCollectQuest(PlayerProfession profession, PlayerQuestData playerData){
        return collectQuestFactory.GenerateCollectQuest(profession, playerData);
    }

    public DifficultyTier GetRandomDifficulty() {
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
    public String GenerateGiverName() {
        // TODO: Complete implementation
        return "The Guild";
    }
}
