package taurasi.marc.allimorequest.Observers;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitTask;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.Config.ConfigWrapper;
import taurasi.marc.allimorequest.Tasks.UnloadPlayerTask;

import java.util.HashMap;

public class PlayerConnectionListener implements Listener {
    private long UNLOADING_DELAY = (20 * 60) * ConfigWrapper.PLAYER_UNLOAD_DATA_DELAY_MINUTES;

    private HashMap<OfflinePlayer, BukkitTask> unloadingTasks;

    public PlayerConnectionListener(){
        unloadingTasks = new HashMap<>(3);
    }

    public void TryCancelUnloadingTask(Player player){
        if(unloadingTasks.containsKey(player)){
            unloadingTasks.get(player).cancel();
            unloadingTasks.remove(player);
        }
    }

    @EventHandler
    public void OnPlayerConnect(PlayerJoinEvent event){
        TryCancelUnloadingTask(event.getPlayer());
        Allimorequest.PLAYER_DATA.LoadPlayer(event.getPlayer());
    }

    @EventHandler
    public void OnPlayerDisconect(PlayerQuitEvent event){
        BukkitTask task = new UnloadPlayerTask(event.getPlayer()).runTaskLater(Allimorequest.INSTANCE, UNLOADING_DELAY);
        unloadingTasks.put(event.getPlayer(), task);
    }

}
