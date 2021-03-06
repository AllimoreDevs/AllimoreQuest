package taurasi.marc.allimorequest;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorecore.CustomConfig;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.ProcGen.QuestFactory;
import taurasi.marc.allimorequest.Tasks.AutoSaveDataTask;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class PlayerDataIndex {
    private ArrayList<PlayerQuestData> questPlayers;
    private CustomConfig configWrapper;
    private QuestFactory questFactory;

    private long autoSaveDelay;

    public PlayerDataIndex(QuestFactory questFactory){
        this.questFactory = questFactory;

        configWrapper = new CustomConfig("PlayerData.yml", Allimorequest.GetInstance().getDataFolder().getPath(), Allimorequest.GetInstance());
        questPlayers = new ArrayList<>();
        autoSaveDelay = (20 * 60) * ConfigWrapper.PLAYER_DATA_AUTOSAVE_INTERVAL;

        BukkitTask autoSaveTask = new AutoSaveDataTask(this).runTaskTimer(Allimorequest.GetInstance(), autoSaveDelay, autoSaveDelay);
    }

    private void AddPlayerData(PlayerQuestData questPlayer){
        questPlayers.add(questPlayer);
    }
    private void RemovePlayerData(OfflinePlayer player){
        PlayerQuestData qp = FindPlayerData(player.getUniqueId());
        if(qp == null){
            AllimoreLogger.LogError("Could not find player!");
            return;
        }
        questPlayers.remove(qp);
    }

    public PlayerQuestData CreateAndAddPlayerData(Player player){
        PlayerQuestData playerData = new PlayerQuestData(player, questFactory);
        questPlayers.add(playerData);
        return playerData;
    }

    private PlayerQuestData FindPlayerData(UUID playerID){
        for(int i = 0; i < questPlayers.size(); i++){
            PlayerQuestData qp = questPlayers.get(i);
            if(qp == null) continue;
            if(qp.GetPlayer().getUniqueId().equals(playerID)) return qp;
        }
        return null;
    }
    public PlayerQuestData GetPlayerData(Player player){
        PlayerQuestData qp = FindPlayerData(player.getUniqueId());
        if(qp == null){
            return CreateAndAddPlayerData(player);
        }else{
            return qp;
        }

    }

    public void LoadPlayer(Player player){
        AddPlayerData(new PlayerQuestData(configWrapper.GetConfig(), player.getUniqueId().toString(), questFactory));
    }
    public void UnloadPlayer(OfflinePlayer player){
        WriteData(player);
        RemovePlayerData(player);
    }

    public void WriteData(OfflinePlayer player){
        FileConfiguration config = configWrapper.GetConfig();
        FindPlayerData(player.getUniqueId()).WriteToConfig(config);
        configWrapper.SaveConfig();
    }
    public void WriteData(){
        FileConfiguration config = configWrapper.GetConfig();

        for(int i = 0; i < questPlayers.size(); i++){
            questPlayers.get(i).WriteToConfig(config);
        }
        configWrapper.SaveConfig();
    }

    @Deprecated
    private void ReadData(){
        Set<String> keys = configWrapper.GetConfig().getKeys(false);
        Object[] uuidKeys = keys.toArray();

        for(int i = 0; i < keys.size(); i++){
            AddPlayerData(new PlayerQuestData(configWrapper.GetConfig(), (String)uuidKeys[i], questFactory));
        }
    }
}
