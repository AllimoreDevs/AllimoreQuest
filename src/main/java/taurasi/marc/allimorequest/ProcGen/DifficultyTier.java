package taurasi.marc.allimorequest.ProcGen;

public enum DifficultyTier {
    NOVICE(30, new Range(3, 7), new Range(32, 64)),
    ADVENTURER(30, new Range(10, 15), new Range(64, 64 * 2)),
    CHAMPION(25, new Range(17, 25), new Range(64 * 3, 64 * 4)),
    LEGEND(15, new Range(25, 30), new Range(64 * 5, 64 * 10));

    public final int appearanceChance;
    public final Range killAmountRange;
    public final Range collectBulkBlockAmountRange;

    DifficultyTier(int appearanceChance, Range killAmountRange, Range collectBulkBlockAmountRange){
        this.appearanceChance = appearanceChance;
        this.killAmountRange = killAmountRange;
        this.collectBulkBlockAmountRange = collectBulkBlockAmountRange;
    }
}
