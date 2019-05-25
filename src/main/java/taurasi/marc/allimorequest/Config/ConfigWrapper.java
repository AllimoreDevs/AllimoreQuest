package taurasi.marc.allimorequest.Config;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.ConversionUtils;
import taurasi.marc.allimorecore.SoundFX;
import taurasi.marc.allimorequest.Database.SQLCredentials;

public class ConfigWrapper {
    private static final double VERSION = 1.2;

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

    // Player Data Settings
    public static int PLAYER_UNLOAD_DATA_DELAY_MINUTES = 5;
    public static int PLAYER_DATA_AUTOSAVE_INTERVAL = 30;

    // GUI Settings
    public static int QUEST_LORE_WRAPPING = 30;
    public static Material QUEST_GUI_ITEM = Material.BOOK;
    public static Material NEGATIVE_BUTTON_ITEM = Material.RED_WOOL;
    public static Material POSITIVE_BUTTON_ITEM = Material.GREEN_WOOL;
    public static ChatColor NEGATIVE_BUTTON_COLOR = ChatColor.RED;
    public static ChatColor POSITIVE_BUTTON_COLOR = ChatColor.GREEN;
    public static ChatColor QUEST_BUTTON_TITLE_COLOR = ChatColor.GOLD;
    public static ChatColor QUEST_BUTTON_LORE_COLOR = ChatColor.YELLOW;

    // Quest Board Settings
    public static int BOARD_QUESTS_TO_GENERATE = 10;
    public static int BOARD_INVENTORY_SIZE = 27;
    public static long BOARD_GENERATION_PERIOD = 60 * 60 * 1000;

    // Info Strings for Command Usage
    public static String INFO_NO_QUEST_NAME_PROVIDED = "Incorrect command usage, please provide the quest name.";
    public static String INFO_CANNOT_FIND_QUEST = "Could not find quest. Make sure to spell the name correctly.";

    public static String INFO_ABANDON_QUEST_USAGE = "/AbandonQuest <QuestName>";
    public static String INFO_COMPLETE_QUEST_USAGE = "/CompleteQuest <QuestName>";
    public static String INFO_FORCE_COMPLETE_QUEST_USAGE = "/ForceCompleteQuest <QuestName>";

    // Info Strings for Quest Journal Usage:
    public static String INFO_EMPTY_QUEST_JOURNAL = "Looks like your Quest Journal is empty right now.";
    public static String INFO_CANNOT_ACCEPT_QUEST_NO_FREE_SLOTS = "You can't accept any more quests right now. Complete or abandon some to make room.";
    public static String INFO_CANNOT_ACCEPT_QUEST_SAME_NAME = "You can't accept that quest right now!";
    public static String INFO_CANNOT_ACCEPT_QUEST_SAME_ENTITY = "Cannot accept the quest at this time. You already have a quest to kill that type of enemy!";
    public static String INFO_CANNOT_COMPLETE_QUEST = "You can't complete that quest at this time.";

    public static boolean NeedsUpdate(FileConfiguration config) {
        double configVersion = config.getDouble("version");
        AllimoreLogger.LogInfo("Config Version: " + configVersion);
        AllimoreLogger.LogInfo("Code Version: " + VERSION);
        AllimoreLogger.LogInfo("Config needs update: " + !(configVersion == VERSION) );

        return !(config.getDouble("version") == VERSION);
    }
    public static SQLCredentials ReadSQLConfig(FileConfiguration config){
        String sqlPath = "mySQL.";
        return new SQLCredentials(
                config.getString(sqlPath + "Host"),
                config.getString(sqlPath + "Database"),
                config.getString(sqlPath + "Username"),
                config.getString(sqlPath + "Password"),
                config.getInt(sqlPath + "Port")
        );
    }
    public static void ReadFromConfig(FileConfiguration config){
        ReadChatNotificationSettings(config);
        ReadSoundNotificationSettings(config);
        ReadTitleNotificationSettings(config);
        ReadPlayerDataSettings(config);
        ReadGUISettings(config);
    }

    private static void ReadChatNotificationSettings(FileConfiguration config){
        String chatSection = "Chat Notifications.";
        QUEST_TITLE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Title Color") );
        QUEST_SUMMARY_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Summary Color"));
        QUEST_OBJECTIVE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Objective Color"));
        QUEST_ABANDOEN_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Abandon Quest Color"));
        QUEST_COMPLETE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(chatSection + "Complete Quest Color"));
    }
    private static void ReadSoundNotificationSettings(FileConfiguration config){
        String soundSection = "Sound Notifications.";
        QUEST_START_SOUNDFX = new SoundFX("Quest Start", config, soundSection);
        QUEST_COMPLETE_SOUNDFX = new SoundFX("Quest Complete", config, soundSection);
        QUEST_ABANDON_SOUNDFX = new SoundFX("Quest Abandon", config, soundSection);
    }
    private static void ReadTitleNotificationSettings(FileConfiguration config){
        String titleSections = "Title Notifications.";
        QUEST_NOTIFICATION_TITLE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(titleSections + "Title Color"));
        QUEST_NOTIFICATION_SUBTITLE_COLOR = ConversionUtils.ConvertStringToColor( config.getString(titleSections + "Subtitle Color"));
        QUEST_NOTIFICATION_FADEIN = GetAndRemap(config,titleSections + "Quest Notification Fade In", 20);
        QUEST_NOTIFICATION_STAY = GetAndRemap(config,titleSections + "Quest Notification Stay", 20);
        QUEST_NOTIFICATION_FADEOUT = GetAndRemap(config,titleSections + "Quest Notification Fade Out", 20);
    }
    private static void ReadPlayerDataSettings(FileConfiguration config){
        String playerData = "Player Data.";
        PLAYER_DATA_AUTOSAVE_INTERVAL = config.getInt(playerData + "Auto Save Interval");
        PLAYER_UNLOAD_DATA_DELAY_MINUTES = config.getInt(playerData + "Player Data Unload Delay");
    }
    private static void ReadGUISettings(FileConfiguration config){
        String path = "GUI Settings.";
        QUEST_LORE_WRAPPING = config.getInt(path + "Lore characters per line");

        QUEST_GUI_ITEM = Material.getMaterial(config.getString(path + "Generic Button Item").toUpperCase(), false);
        QUEST_BUTTON_TITLE_COLOR = ConversionUtils.ConvertStringToColor(config.getString(path + "Generic Title Color"));
        QUEST_BUTTON_LORE_COLOR = ConversionUtils.ConvertStringToColor(config.getString(path + "Genertic Lore Color"));
        POSITIVE_BUTTON_ITEM = Material.getMaterial(config.getString(path + "Positive Button Item").toUpperCase(),false);
        POSITIVE_BUTTON_COLOR = ConversionUtils.ConvertStringToColor(config.getString(path + "Positive Button Color"));
        NEGATIVE_BUTTON_ITEM = Material.getMaterial(config.getString(path + "Negative Button Item").toUpperCase(),false);
        NEGATIVE_BUTTON_COLOR = ConversionUtils.ConvertStringToColor(config.getString(path + "Negative Button Color"));
    }

    private static int GetAndRemap(FileConfiguration config, String path, int multiplier){
        return (int)Math.floor(config.getDouble(path) * multiplier);
    }
}
