package taurasi.marc.allimorequest.GUI;
import taurasi.marc.allimorecore.GUI.Button;
import taurasi.marc.allimorecore.GUI.InventoryGUI;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.Quest;

public class QuestButton extends Button {
    private Quest quest;
    private QuestButtonListener listener;

    public QuestButton(Quest quest, InventoryGUI inv, QuestButtonListener listener) {
        super(inv.CreatePreformatedItem(quest.GetQuestName(), quest.GetSummary(), ConfigWrapper.QUEST_GUI_ITEM), inv);
        this.quest = quest;
        this.listener = listener;
    }

    @Override
    public void Run() {
        listener.OnQuestButton(quest);
    }
}
