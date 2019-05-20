package taurasi.marc.allimorequest.ProcGen;

public enum DifficultyTier {
    NOVICE(30, 3, 7),
    ADVENTURER(30, 10, 15),
    CHAMPION(25, 17, 25),
    LEGEND(15, 25, 30);

    public final int appearanceChance;
    public final Range killAmountRange;

    DifficultyTier(int appearanceChance, int minKillAmount, int maxKillAmount){
        this.appearanceChance = appearanceChance;
        this.killAmountRange = new Range(minKillAmount, maxKillAmount);
    }
}
