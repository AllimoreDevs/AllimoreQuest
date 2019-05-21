package taurasi.marc.allimorequest.ProcGen;

import taurasi.marc.allimorecore.Range;

public enum DifficultyTier {
    NOVICE(30,
            new Range(3, 7),
            new Range(64, 64 * 3),
            new Range(32, 64),
            new Range(7, 32)),
    ADVENTURER(30,
            new Range(10, 15),
            new Range(64 * 3, 64 * 6),
            new Range(64, 64 * 3),
            new Range(32, 96)),
    CHAMPION(25,
            new Range(17, 25),
            new Range(64 * 6, 64 * 12),
            new Range(64 * 3, 64 * 5),
            new Range(96, 64 * 3)),
    LEGEND(15,
            new Range(25, 30),
            new Range(64 * 12, 64 * 24),
            new Range(64 * 5, 64 * 10),
            new Range(64 *3, 64 * 5));

    public final int appearanceChance;
    public final Range killAmountRange;
    public final Range collectBulkBlockAmountRange;
    public final Range collectLogsBlockAmountRange;
    public final Range collectOreAmountRange;

    DifficultyTier(int appearanceChance, Range killAmountRange, Range collectBulkBlockAmountRange, Range collectLogsBlockAmountRange, Range collectOreAmountRange){
        this.appearanceChance = appearanceChance;
        this.killAmountRange = killAmountRange;
        this.collectBulkBlockAmountRange = collectBulkBlockAmountRange;
        this.collectLogsBlockAmountRange = collectLogsBlockAmountRange;
        this.collectOreAmountRange = collectOreAmountRange;
    }
}
