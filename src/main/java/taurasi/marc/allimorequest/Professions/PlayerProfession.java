package taurasi.marc.allimorequest.Professions;

public enum PlayerProfession {
    WOODCUTTER, MINER, EXCAVATOR, SENTINEL, FARMER, FISHER;

    public static boolean Contains(String name){
        PlayerProfession[] professions = values();
        for(PlayerProfession profession : professions){
            if(profession.name().equalsIgnoreCase(name)) return true;
        }
        return false;
    }
}
