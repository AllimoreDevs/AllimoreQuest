package taurasi.marc.allimorequest.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Allimorequest;

import java.util.logging.Level;

public class QuestLogger {
    private static ChatColor ERROR_COLOR = ChatColor.RED;
    private static ChatColor INFO_COLOR = ChatColor.YELLOW;

    public static void LogInfo(String message, Player player){
        player.sendMessage(String.format("%s%s", INFO_COLOR, message));
        LogInfo(message);
    }
    public static void LogInfo(String message){
        Allimorequest.INSTANCE.getLogger().log(Level.INFO, message);
    }

    public static void LogError(String message, Player player){
        player.sendMessage(String.format("%s%s", ERROR_COLOR, message));
        LogError(message);
    }
    public static void LogError(String message){
        Allimorequest.INSTANCE.getLogger().log(Level.WARNING, message);
    }
}
