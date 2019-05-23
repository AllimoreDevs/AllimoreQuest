package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.entity.Villager;
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
        try {
            switch (profession) {
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
                case FISHER:
                    return collectQuestFactory.GenerateFisherQuest(playerData, difficultyTier);
            }
        }catch (Exception e){
            AllimoreLogger.LogError("Failed to generate quest!", playerData.GetOnlinePlayer());
        }
        return null;
    }
    public Quest GenerateQuest(PlayerProfession profession, PlayerQuestData playerData){
        return GenerateQuest(profession, playerData, GetRandomDifficulty());
    }
    public Quest GenerateQuest(PlayerQuestData playerData){
        return GenerateQuest(GetRandomProfession(), playerData, GetRandomDifficulty());
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
}
