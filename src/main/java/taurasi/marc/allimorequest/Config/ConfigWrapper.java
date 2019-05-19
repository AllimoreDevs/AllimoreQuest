package taurasi.marc.allimorequest.Config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.ConversionUtils;
import taurasi.marc.allimorecore.SoundFX;

public class ConfigWrapper {
    // Chat Notificaiton Settings
    public static ChatColor QUEST_TITLE_COLOR = ChatColor.GOLD;
    public static ChatColor QUEST_SUMMARY_COLOR = ChatColor.YELLOW;
    public static ChatColor QUEST_OBJECTIVE_COLOR = ChatColor.RED;
    public static ChatColor QUEST_ABANDOEN_COLOR = ChatColor.RED;
    public static ChatColor QUEST_COMPLETE_COLOR = ChatColor.GREEN;

    // Title Notification Settings
    public static int QUEST_NOTIFICATION_FADEIN = 10;
    public static int QUEST_NOTIFICATION_STAY = 20;
    public static int QUEST_NOTIFICATION_FADEOUT = 10;
    public static ChatColor QUEST_NOTIFICATION_TITLE_COLOR = ChatColor.GREEN;
    public static ChatColor QUEST_NOTIFICATION_SUBTITLE_COLOR = ChatColor.WHITE;

    // Sound Notification Settings
    public static SoundFX QUEST_START_SOUNDFX = new SoundFX("Quest Start", Sound.ENTITY_ARROW_HIT_PLAYER, 1f, .5f);
    public static SoundFX QUEST_COMPLETE_SOUNDFX = new SoundFX("Quest Complete", Sound.ENTITY_PLAYER_LEVELUP, 1f, 1.3f);
    public static SoundFX QUEST_ABANDON_SOUNDFX = new SoundFX("Quest Abandon", Sound.ENTITY_ARROW_HIT_PLAYER, 1f, .5f);

    // Player Data Settings;
    public static int PLAYER_UNLOAD_DATA_DELAY_MINUTES = 5;
    public static int PLAYER_DATA_AUTOSAVE_INTERVAL = 30;

    // GUI Settings
    public static int QUEST_LORE_WRAPPING = 30;
    public static Material QUEST_GUI_ITEM = Material.BOOK;
    public static Material DANGEROUS_BUTTON_ITEM = Material.RED_WOOL;
    public static Material POSITIVE_BUTTON_ITEM = Material.GREEN_WOOL;
    public static ChatColor DANGEROUS_BUTTON_COLOR = ChatColor.RED;
    public static ChatColor POSITIVE_BUTTON_COLOR = ChatColor.GREEN;
    public static ChatColor QUEST_BUTTON_TITLE_COLOR = ChatColor.GOLD;
    public static ChatColor QUEST_BUTTON_LORE_COLOR = ChatColor.YELLOW;

    // Info Strings for Command Usage
    public static String INFO_NO_QUEST_NAME_PROVIDED = "Incorrect command usage, please provide the quest name.";
    public static String INFO_CANNOT_FIND_QUEST = "Could not find quest. Make sure to spell the name correctly.";

    public static String INFO_ABANDON_QUEST_USAGE = "/AbandonQuest <QuestName>";
    public static String INFO_COMPLETE_QUEST_USAGE = "/CompleteQuest <QuestName>";
    public static String INFO_FORCE_COMPLETE_QUEST_USAGE = "/ForceCompleteQuest <QuestName>";

    // Info Strings for Quest Journal Usage:
    public static String INFO_EMPTY_QUEST_JOURNAL = "Looks like your Quest Journal is empty right now.";
    public static String INFO_CANNOT_ACCEPT_QUEST_NO_FREE_SLOTS = "You can't accept any more quests right now. Complete or abandon some to make room.";
    public static String INFO_CANNOT_COMPLETE_QUEST = "You can't complete that quest at this time.";

    public static void ReadFromConfig(FileConfiguration config){
        String chatSection = "Chat Notifications.";
        QUEST_TITLE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Title Color") );
        QUEST_SUMMARY_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Summary Color"));
        QUEST_OBJECTIVE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Objective Color"));
        QUEST_ABANDOEN_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Abandon Quest Color"));
        QUEST_COMPLETE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Complete Quest Color"));

        String soundSection = "Sound Notifications.";
        QUEST_START_SOUNDFX = new SoundFX("Quest Start", config, soundSection);
        QUEST_COMPLETE_SOUNDFX = new SoundFX("Quest Complete", config, soundSection);
        QUEST_ABANDON_SOUNDFX = new SoundFX("Quest Abandon", config, soundSection);

        String titleSections = "Title Notifications.";
        QUEST_NOTIFICATION_TITLE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(titleSections + "Title Color"));
        QUEST_NOTIFICATION_SUBTITLE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(titleSections + "Subtitle Color"));
        QUEST_NOTIFICATION_FADEIN = config.getInt(titleSections + "Quest Notification Fade In");
        QUEST_NOTIFICATION_STAY = config.getInt(titleSections + "Quest Notification Stay");
        QUEST_NOTIFICATION_FADEOUT = config.getInt(titleSections + "Quest Notification Fade Out");

        String playerData = "Player Data.";
        PLAYER_DATA_AUTOSAVE_INTERVAL = config.getInt(playerData + "Auto Save Interval");
        PLAYER_UNLOAD_DATA_DELAY_MINUTES = config.getInt(playerData + "Player Data Unload Delay");
    }
}
