package taurasi.marc.allimorequest.ProcGen;

public enum DifficultyTier {
    NOVICE(30, new Range(3, 7)),
    ADVENTURER(30, new Range(10, 15)),
    CHAMPION(25, new Range(17, 25)),
    LEGEND(15, new Range(25, 30));

    public final int appearanceChance;
    public final Range killAmountRange;

    DifficultyTier(int appearanceChance, Range killAmountRange){
        this.appearanceChance = appearanceChance;
        this.killAmountRange = killAmountRange;
    }
}
