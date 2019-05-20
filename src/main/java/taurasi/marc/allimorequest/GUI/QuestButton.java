package taurasi.marc.allimorequest.GUI;

import org.bukkit.Material;
import taurasi.marc.allimorecore.GUI.Button;
import taurasi.marc.allimorecore.GUI.InventoryGUI;
import taurasi.marc.allimorequest.Quest;

public class QuestButton extends Button {
    private Quest quest;

    public QuestButton(Quest quest, InventoryGUI inv) {
        super(inv.CreatePreformatedItem(quest.GetQuestName(), quest.QuestFullSummary(), Material.BOOK), inv);
        this.quest = quest;
    }

    @Override
    public void Run() {
        QuestJournalGUI gui = (QuestJournalGUI)inv;
        gui.OpenQuestDataPanel(quest);
    }
}
