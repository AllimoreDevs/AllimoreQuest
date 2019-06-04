package taurasi.marc.allimorequest;

import org.bukkit.entity.Player;

public interface NotificationService {
    void PlayStartNotification();
    void PlayCompleteNotification();
    void PlayAbandonNotification();
    void DisplayQuestBriefInChat(Player inquirer);
    void DisplayQuestStatusInChat(Player inquirer);
    void DisplayPayoutInChat(Player inquirer, double payOut);
}
