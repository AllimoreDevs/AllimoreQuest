package taurasi.marc.allimorequest;

import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Config.ConfigWrapper;

public class QuestNotifications implements NotificationService {
    private Quest quest;

    public QuestNotifications(Quest quest){
        this.quest = quest;
    }

    @Override
    public void PlayStartNotification() {
        Player player = quest.GetOnlinePlayer();
        ConfigWrapper.QUEST_START_SOUNDFX.PlaySound(player);
        DisplayTitleNotification(quest.GetQuestName(), quest.GetCurrentObjective().GetName());

        DisplayQuestBriefInChat(player);
    }
    @Override
    public void PlayCompleteNotification() {
        Player player = quest.GetOnlinePlayer();
        ConfigWrapper.QUEST_COMPLETE_SOUNDFX.PlaySound(player);
        DisplayTitleNotification(quest.GetQuestName(), "Completed!");
        SendQuestCompleteToChat(player);
    }
    @Override
    public void PlayAbandonNotification() {
        Player player = quest.GetOnlinePlayer();
        ConfigWrapper.QUEST_ABANDON_SOUNDFX.PlaySound(player);
        DisplayTitleNotification(quest.GetQuestName(), ConfigWrapper.QUEST_ABANDOEN_COLOR + "Abandoned!");
        SendQuestAbandonedToChat(player);
    }

    public void DisplayQuestBriefInChat (Player inquirer){
        if( !inquirer.isOnline() ) return;

        SendQuestBriefToChat(inquirer);
        SendObjectiveProgressToChat(inquirer);
    }
    public void DisplayQuestStatusInChat (Player inquirer){
        if( !inquirer.isOnline() ) return;
        inquirer.sendMessage( String.format("%s %s%s %s", quest.QuestFullName(), ConfigWrapper.QUEST_OBJECTIVE_COLOR, quest.GetCurrentObjective().GetName(), quest.GetCurrentObjective().GetProgress()) );
    }

    private void SendQuestBriefToChat(Player inquirer){
        inquirer.sendMessage( String.format("%s %s", quest.QuestFullName(), quest.QuestFullSummary()) );
    }
    private void SendObjectiveProgressToChat(Player inquirer){
        inquirer.sendMessage( String.format("%s%s: %s", ConfigWrapper.QUEST_OBJECTIVE_COLOR, quest.GetCurrentObjective().GetName(), quest.GetCurrentObjective().GetProgress()) );
    }
    private void SendNewObjectiveToChat(Player inquirer){
        inquirer.sendMessage(String.format("%s %sNew Objective!", quest.QuestFullName(), ConfigWrapper.QUEST_SUMMARY_COLOR));
        SendObjectiveProgressToChat(inquirer);
    }
    private void SendQuestCompleteToChat(Player inquirer){
        inquirer.sendMessage(quest.QuestFullDescription());
        inquirer.sendMessage(String.format("%sCompleted!", ConfigWrapper.QUEST_COMPLETE_COLOR));
    }
    private void SendQuestAbandonedToChat(Player inquirer){
        inquirer.sendMessage(quest.QuestFullDescription());
        inquirer.sendMessage(String.format("%sAbadoned!", ConfigWrapper.QUEST_ABANDOEN_COLOR));
    }

    private void DisplayTitleNotification(String title, String subtitle){
        quest.GetOnlinePlayer().sendTitle(
                ConfigWrapper.QUEST_NOTIFICATION_TITLE_COLOR + title,
                ConfigWrapper.QUEST_NOTIFICATION_SUBTITLE_COLOR + subtitle,
                ConfigWrapper.QUEST_NOTIFICATION_FADEIN,
                ConfigWrapper.QUEST_NOTIFICATION_STAY,
                ConfigWrapper.QUEST_NOTIFICATION_FADEOUT);
    }
}
