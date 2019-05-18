package taurasi.marc.allimorequest.Objectives;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.EntityDeathObserver;
import taurasi.marc.allimorequest.Quest;

import java.util.UUID;

public class KillPlayerObjective extends Objective implements EntityDeathObserver {
    private OfflinePlayer targetPlayer;
    private boolean killedPlayer;

    public KillPlayerObjective(String name, Quest quest, Player targetPlayer) {
        super(name, quest);
        this.targetPlayer = targetPlayer;

        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    public KillPlayerObjective(FileConfiguration config, String path, String name, Quest quest){
        super(name, quest);
        UUID id = UUID.fromString(config.getString(path + "Target"));
        targetPlayer = Allimorequest.INSTANCE.getServer().getOfflinePlayer(id);
        killedPlayer = config.getBoolean(path + "Killed");

        Allimorequest.EVENT_LISTENER.Subscribe(this);
    }
    @Override
    public void WriteToConfig(FileConfiguration config, String section){
        super.WriteToConfig(config, section);
        config.set(section + "Target", targetPlayer.getUniqueId());
        config.set(section + "Killed", killedPlayer);
    }

    @Override
    public boolean IsComplete() {
        if(killedPlayer){
            Allimorequest.EVENT_LISTENER.Unsubscribe(this);
        }
        return killedPlayer;
    }

    @Override
    public String GetProgress() {
        if(killedPlayer) {
            return String.format("%s has been silenced.", targetPlayer.getName());
        } else{
            return String.format("%s is still alive.", targetPlayer.getName());
        }
    }

    @Override
    public ObjectiveType GetType() {
        return ObjectiveType.KILL_PLAYER;
    }

    @Override
    public void Notify(EntityDeathEvent event) {
        if( !(event.getEntity() instanceof Player) ) return;
        Player player = (Player)event.getEntity();
        if(player == targetPlayer){
            killedPlayer = true;
            quest.CompleteQuest();
        }
    }
}
