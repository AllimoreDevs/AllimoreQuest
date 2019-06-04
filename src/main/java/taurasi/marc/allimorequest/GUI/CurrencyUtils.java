package taurasi.marc.allimorequest.GUI;

import java.text.NumberFormat;

public class CurrencyUtils {
    public static String ParseCurrency(double rawValue){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(rawValue);
    }
}
