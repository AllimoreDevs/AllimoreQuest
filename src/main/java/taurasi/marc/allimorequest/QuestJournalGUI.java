package taurasi.marc.allimorequest;

import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.Utils.Utils;

import java.util.ArrayList;

public class QuestJournalGUI implements InventoryClickObserver {
    private final Inventory inv;
    private PlayerQuestData playerData;

    private boolean displayQuestMenu = true;
    private Quest[] slotedQuests;
    private Quest displayedQuest;

    public QuestJournalGUI(PlayerQuestData playerData){
        inv = Bukkit.createInventory(null, 9);
        this.playerData = playerData;
        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }

    public void OpenGUI(Player player){
        player.openInventory(inv);
        OpenQuestMenu();
    }

    private void OpenQuestMenu(){
        displayQuestMenu = true;
        displayedQuest = null;
        PopulateInventoryWithQuestButtons();

    }
    private void OpenQuestDataPanel(Quest quest){
        displayQuestMenu = false;
        PopulateInventoryWithQuestData(quest);
    }

    private void PopulateInventoryWithQuestButtons(){
        inv.clear();

        slotedQuests = playerData.GetQuests();

        for(int i = 0; i < slotedQuests.length; i++){
            if(slotedQuests[i] == null) continue;
            inv.addItem(CreateQuestButton(slotedQuests[i]));
        }
    }
    private void PopulateInventoryWithQuestData(Quest quest){
        inv.clear();
        displayedQuest = quest;

        String itemTitle = ConfigWrapper.QUEST_BUTTON_TITLE_COLOR + quest.GetQuestName();
        ArrayList<String> itemLore = Utils.WrapLore(quest.GetSummary(), ConfigWrapper.QUEST_LORE_WRAPPING, ConfigWrapper.QUEST_BUTTON_LORE_COLOR.toString());

        inv.addItem(CreateGUIItem(itemTitle, itemLore, ConfigWrapper.QUEST_GUI_ITEM));

        itemTitle = ConfigWrapper.QUEST_BUTTON_TITLE_COLOR + quest.GetCurrentObjective().GetName();
        String itemLoreText = ConfigWrapper.QUEST_BUTTON_LORE_COLOR + quest.GetCurrentObjective().GetProgress();
        itemLore.clear();
        itemLore.add(itemLoreText);

        inv.addItem(CreateGUIItem(itemTitle, itemLore, ConfigWrapper.QUEST_GUI_ITEM));

        itemTitle = ConfigWrapper.DANGEROUS_BUTTON_COLOR + "Abandon Quest";
        itemLore = Utils.WrapLore("Abandons this quest, removing it from your quest journal.", ConfigWrapper.QUEST_LORE_WRAPPING, ConfigWrapper.QUEST_BUTTON_LORE_COLOR.toString());

        inv.addItem(CreateGUIItem(itemTitle, itemLore, ConfigWrapper.DANGEROUS_BUTTON_ITEM));

        itemTitle = ConfigWrapper.POSITIVE_BUTTON_COLOR + "Complete Quest";
        itemLore = Utils.WrapLore("Attempts to complete the quest, collect quests will automatically consume items.", ConfigWrapper.QUEST_LORE_WRAPPING, ConfigWrapper.QUEST_BUTTON_LORE_COLOR.toString());

        inv.addItem(CreateGUIItem(itemTitle, itemLore, ConfigWrapper.POSITIVE_BUTTON_ITEM));

        itemTitle = ConfigWrapper.DANGEROUS_BUTTON_COLOR + "Go Back";
        itemLoreText = ConfigWrapper.QUEST_BUTTON_LORE_COLOR + "Takes you back to the previous menu.";
        itemLore.clear();
        itemLore.add(itemLoreText);

        inv.addItem(CreateGUIItem(itemTitle, itemLore, ConfigWrapper.DANGEROUS_BUTTON_ITEM));
    }

    private ItemStack CreateQuestButton(Quest quest){
        String name = ConfigWrapper.QUEST_BUTTON_TITLE_COLOR + quest.GetQuestName();
        String objectiveText = ConfigWrapper.QUEST_BUTTON_LORE_COLOR + quest.GetCurrentObjective().GetName();

        ArrayList<String> lore = new ArrayList<String>(1);
        lore.add(objectiveText);

        return CreateGUIItem(name, lore, ConfigWrapper.QUEST_GUI_ITEM);
    }
    private ItemStack CreateGUIItem(String name, ArrayList<String> lore, Material material){
        ItemStack i = new ItemStack(material, 1);
        ItemMeta iMeta = i.getItemMeta();
        iMeta.setDisplayName(name);
        iMeta.setLore(lore);
        i.setItemMeta(iMeta);
        return i;
    }

    public void Notify(InventoryClickEvent event){
        if( !(event.getInventory().equals(inv)) ) return;
        event.setCancelled(true);

        ItemStack clickedItem = event.getCurrentItem();

        if(clickedItem == null || clickedItem.getType().equals(Material.AIR)) return;
        if(displayQuestMenu){
            OpenQuestDataPanel(slotedQuests[event.getRawSlot()]);
        }else {
            switch(event.getRawSlot()){
                case 2:
                    playerData.AbandonQuest(displayedQuest);
                    OpenQuestMenu();
                    return;
                case 3:
                    if(playerData.TryCompleteQuestObjective(displayedQuest)){
                        OpenQuestMenu();
                    }else{
                        return;
                    }
                    break;
                case 4:
                    OpenQuestMenu();
                    return;
            }
        }
    }
}
