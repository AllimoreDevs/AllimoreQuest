package taurasi.marc.allimorequest.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import taurasi.marc.allimorecore.GUI.GUIEventRouter;
import taurasi.marc.allimorecore.GUI.InventoryGUI;
import taurasi.marc.allimorecore.GUI.StandardButton;
import taurasi.marc.allimorecore.GUI.StandardButtonListener;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.Quest;

public class QuestJournalGUI extends InventoryGUI implements StandardButtonListener, QuestButtonListener {
    private PlayerQuestData playerData;
    private Quest displayedQuest = null;

    public QuestJournalGUI(int inventorySize, GUIEventRouter router, PlayerQuestData playerData) {
        super(inventorySize, router);
        characterPerLoreLine = ConfigWrapper.QUEST_LORE_WRAPPING;
        itemTitleColor = ConfigWrapper.QUEST_BUTTON_TITLE_COLOR;
        loreColor = ConfigWrapper.QUEST_BUTTON_LORE_COLOR;
        this.playerData = playerData;
    }

    @Override
    public void OpenGUI(Player player){
        super.OpenGUI(player);
        OpenQuestMenu();
    }

    private void OpenQuestMenu(){
        displayedQuest = null;
        PopulateInventoryWithQuestButtons();
    }
    @Override
    public void OnQuestButton(Quest quest){
        PopulateInventoryWithQuestData(quest);
    }

    private void PopulateInventoryWithQuestButtons() {
        ClearInv();
        Quest[] quests = playerData.GetQuests();
        for(Quest quest : quests){
            if(quest == null) continue;;
            CreateAndAddButton(new QuestButton(quest,this, this));
        }
    }
    private void PopulateInventoryWithQuestData(Quest quest){
        ClearInv();
        displayedQuest = quest;

        ItemStack itemStack = CreatePreformatedItem(quest.GetQuestName(), quest.GetSummary(), Material.BOOK);
        inv.addItem(itemStack);
        itemStack = CreatePreformatedItem(quest.GetCurrentObjective().GetName(), quest.GetCurrentObjective().GetProgress(), Material.BOOK);
        inv.addItem(itemStack);

        itemStack = CreateGUIItem(ConfigWrapper.NEGATIVE_BUTTON_COLOR + "Abandon Quest",
                "Abandons this quest, removing it from your quest journal.",
                ConfigWrapper.NEGATIVE_BUTTON_ITEM);
        CreateAndAddButton(new StandardButton("Abandon Quest", itemStack, this, this));

        itemStack = CreateGUIItem(ConfigWrapper.POSITIVE_BUTTON_COLOR + "Complete Quest",
                "Attempts to complete the quest, collect quests will automatically consume items.",
                ConfigWrapper.POSITIVE_BUTTON_ITEM);
        CreateAndAddButton(new StandardButton("Complete Quest", itemStack, this, this));

        itemStack = CreateGUIItem(ConfigWrapper.NEGATIVE_BUTTON_COLOR + "Go Back",
                "Takes you back to the previous menu.",
                ConfigWrapper.NEGATIVE_BUTTON_ITEM);
        CreateAndAddButton(new StandardButton("Go Back", itemStack, this, this));
    }

    @Override
    public void OnButtonClick(String name) {
        switch(name){
            case "Abandon Quest":
                playerData.AbandonQuest(displayedQuest);
                OpenQuestMenu();
                return;
            case "Complete Quest":
                if(playerData.TryCompleteQuestObjective(displayedQuest)){
                    OpenQuestMenu();
                }else{
                    return;
                }
            case "Go Back":
                OpenQuestMenu();
        }
    }
}
