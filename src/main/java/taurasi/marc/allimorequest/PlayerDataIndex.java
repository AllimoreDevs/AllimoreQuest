package taurasi.marc.allimorequest;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import taurasi.marc.allimorequest.Config.QuestDataWrapper;

import java.util.ArrayList;
import java.util.Set;

public class PlayerDataIndex {
    private ArrayList<PlayerQuestData> questPlayers;
    private QuestDataWrapper wrapper;

    public PlayerDataIndex(){
        questPlayers = new ArrayList<PlayerQuestData>();
        wrapper = new QuestDataWrapper();
        ReadData();
    }

    public void AddPlayerData(PlayerQuestData questPlayer){
        questPlayers.add(questPlayer);
    }
    public PlayerQuestData CreateAndAddPlayerData(Player player){
        PlayerQuestData playerData = new PlayerQuestData(player);
        questPlayers.add(playerData);
        return playerData;
    }

    public PlayerQuestData GetPlayerData(Player player){
        for(int i = 0; i < questPlayers.size(); i++){
            PlayerQuestData qp = questPlayers.get(i);
            if(qp == null) continue;
            if(qp.GetOnlinePlayer() == player) return qp;
        }
        return CreateAndAddPlayerData(player);
    }

    public void WriteData(){
        YamlConfiguration config = new YamlConfiguration();

        for(int i = 0; i < questPlayers.size(); i++){
            questPlayers.get(i).WriteToConfig(config);
        }
        wrapper.SaveOverConfig(config);
    }
    public void ReadData(){
        Set<String> keys = wrapper.GetConfig().getKeys(false);
        Object[] uuidKeys = keys.toArray();

        for(int i = 0; i < keys.size(); i++){
            AddPlayerData(new PlayerQuestData(wrapper.GetConfig(), (String)uuidKeys[i]));
        }
    }
}
