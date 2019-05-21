package taurasi.marc.allimorequest.ProcGen;

import taurasi.marc.allimorecore.Range;

public enum DifficultyTier {
    NOVICE(30,
            new Range(3, 7),
            new Range(64, 64 * 3),
            new Range(32, 64)),
    ADVENTURER(30,
            new Range(10, 15),
            new Range(64 * 3, 64 * 6),
            new Range(64, 64 * 3)),
    CHAMPION(25,
            new Range(17, 25),
            new Range(64 * 6, 64 * 12),
            new Range(64 * 3, 64 * 5)),
    LEGEND(15,
            new Range(25, 30),
            new Range(64 * 12, 64 * 24),
            new Range(64 * 5, 64 * 10));

    public final int appearanceChance;
    public final Range killAmountRange;
    public final Range collectBulkBlockAmountRange;
    public final Range collectLogsBlockAmountRange;

    DifficultyTier(int appearanceChance, Range killAmountRange, Range collectBulkBlockAmountRange, Range collectLogsBlockAmountRange){
        this.appearanceChance = appearanceChance;
        this.killAmountRange = killAmountRange;
        this.collectBulkBlockAmountRange = collectBulkBlockAmountRange;
        this.collectLogsBlockAmountRange = collectLogsBlockAmountRange;
    }
}
