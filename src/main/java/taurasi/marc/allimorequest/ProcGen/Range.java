package taurasi.marc.allimorequest.ProcGen;

import java.security.InvalidParameterException;

public class Range {
    public int min;
    public int max;

    public Range(int min, int max){
        if (min >= max) {
            throw new InvalidParameterException("Min cannot be greater than or equal to max!");
        }
        this.min = min;
        this.max = max;
    }
}
