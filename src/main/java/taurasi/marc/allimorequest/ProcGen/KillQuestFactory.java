package taurasi.marc.allimorequest.ProcGen;

import org.bukkit.entity.EntityType;
import taurasi.marc.allimorecore.RandomUtils;
import taurasi.marc.allimorecore.StringUtils;
import taurasi.marc.allimorequest.Objectives.KillObjective;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Quest;

public class KillQuestFactory {
    private EntityType[] hostileTypes = new EntityType[]{
            EntityType.ZOMBIE, EntityType.CREEPER, EntityType.SKELETON, EntityType.SPIDER
    };
    private QuestFactory questFactory;

    public KillQuestFactory(QuestFactory questFactory){
        this.questFactory = questFactory;
    }

    public Quest GenerateKillQuest(PlayerQuestData playerData, DifficultyTier difficulty){
        String questGiver = questFactory.GenerateGiverName();
        Quest quest = new Quest(questGiver, playerData);
        quest.SetCurrentObjective(GenerateKillObjective(quest, difficulty));

        questFactory.flairGenerator.SetKillQuestFlair(quest, playerData);
        return quest;
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
}
