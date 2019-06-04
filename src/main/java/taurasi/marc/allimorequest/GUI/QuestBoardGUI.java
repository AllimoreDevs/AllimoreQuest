package taurasi.marc.allimorequest.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.GUI.GUIEventRouter;
import taurasi.marc.allimorecore.GUI.InventoryGUI;
import taurasi.marc.allimorecore.GUI.StandardButton;
import taurasi.marc.allimorecore.GUI.StandardButtonListener;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.PlayerQuestData;
import taurasi.marc.allimorequest.ProcGen.QuestCollection;
import taurasi.marc.allimorequest.ProcGen.QuestFactory;
import taurasi.marc.allimorequest.Quest;

public class QuestBoardGUI extends InventoryGUI implements StandardButtonListener, QuestButtonListener, QuestCollection {
    private PlayerQuestData playerData;
    private QuestFactory questFactory;
    private Quest[] quests;
    private Quest displayedQuest;
    private long lastGenerationTime = 0;

    public QuestBoardGUI(GUIEventRouter router, PlayerQuestData playerData, QuestFactory questFactory) {
        super(ConfigWrapper.BOARD_INVENTORY_SIZE, router);
        quests = new Quest[ConfigWrapper.BOARD_QUESTS_TO_GENERATE];
        characterPerLoreLine = ConfigWrapper.QUEST_LORE_WRAPPING;
        itemTitleColor = ConfigWrapper.QUEST_BUTTON_TITLE_COLOR;
        loreColor = ConfigWrapper.QUEST_BUTTON_LORE_COLOR;
        this.playerData = playerData;
        this.questFactory = questFactory;
    }

    @Override
    public void OpenGUI(Player player){
        super.OpenGUI(player);
        if(System.currentTimeMillis() - lastGenerationTime > ConfigWrapper.BOARD_GENERATION_PERIOD){
            GenerateQuests(playerData);
        }
        PopulateBoard();
    }

    public void GenerateQuests(PlayerQuestData playerData){
        for(int i = 0; i < ConfigWrapper.BOARD_QUESTS_TO_GENERATE; i++){
            try {
                quests[i] = questFactory.GenerateQuest(playerData, this);
            } catch (Exception e) {
                AllimoreLogger.LogInfo("Quest Board quest generation failed!");
            }
            if(quests[i] == null) i--;
        }
        lastGenerationTime = System.currentTimeMillis();
    }

    public void OpenQuestBoard(){
        displayedQuest = null;
        PopulateBoard();
    }

    public void PopulateBoard(){
        ClearInv();
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
        itemStack = CreatePreformatedItem("Quest Reward", CurrencyUtils.ParseCurrency(quest.GetQuestRewardMoney()) + " Alms", Material.PAPER);
        inv.addItem(itemStack);

        itemStack = CreateGUIItem(ConfigWrapper.POSITIVE_BUTTON_COLOR + "Accept Quest",
                "Accepts the quest if adds it to your quest journal.",
                ConfigWrapper.POSITIVE_BUTTON_ITEM);
        CreateAndAddButton(new StandardButton("Accept Quest", itemStack, this, this));

        itemStack = CreateGUIItem(ConfigWrapper.NEGATIVE_BUTTON_COLOR + "Go Back",
                "Takes you back to the previous menu.",
                ConfigWrapper.NEGATIVE_BUTTON_ITEM);
        CreateAndAddButton(new StandardButton("Go Back", itemStack, this, this));
    }

    private void RemoveQuest(Quest quest){
        for(int i = 0; i < quests.length; i++){
            if(quests[i].equals(quest)){
                quests[i] =  null;
                return;
            }
        }
    }

    @Override
    public void OnQuestButton(Quest quest) {
        displayedQuest = quest;
        PopulateInventoryWithQuestData(quest);
    }
    @Override
    public void OnButtonClick(String name) {
        switch(name){
            case "Accept Quest":
                if(playerData.AcceptQuest(displayedQuest)){
                    RemoveQuest(displayedQuest);
                    displayedQuest = null;
                    OpenQuestBoard();
                }
                return;
            case "Go Back":
                OpenQuestBoard();
                return;
        }
    }


    @Override
    public boolean ContainsQuestName(String questName) {
        for(Quest quest : quests){
            if(quest == null) continue;
            if(quest.GetQuestName().equalsIgnoreCase(questName))
                return true;
        }
        return false;
    }
}
