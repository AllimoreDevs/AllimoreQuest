package taurasi.marc.allimorequest.Tasks;

import org.bukkit.scheduler.BukkitRunnable;
import taurasi.marc.allimorecore.AllimoreLogger;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.PlayerDataIndex;

public class AutoSaveDataTask extends BukkitRunnable {
    private PlayerDataIndex playerDataIndex;

    public AutoSaveDataTask(PlayerDataIndex playerDataIndex){
        this.playerDataIndex = playerDataIndex;
    }

    @Override
    public void run() {
        playerDataIndex.WriteData();
        AllimoreLogger.LogInfo("Auto-Saving player data.");
    }
}
