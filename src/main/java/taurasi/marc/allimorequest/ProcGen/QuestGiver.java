package taurasi.marc.allimorequest.ProcGen;

public class QuestGiver {
    public final static String[] malePronouns =  new String[]{"He", "His"};
    public final static String[] femalePronouns =  new String[]{"She", "Her"};

    public String name;
    public boolean isMale;

    public QuestGiver(String name , boolean isMale){
        this.name = name;
        this.isMale = isMale;
    }

    public String GetPronoun(){
        return (isMale) ? malePronouns[0] : femalePronouns[0];
    }
    public String GetPossessivePronoun(){
        return (isMale) ? malePronouns[1] : femalePronouns[1];
    }
}
