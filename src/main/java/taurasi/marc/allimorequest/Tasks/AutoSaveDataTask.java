package taurasi.marc.allimorequest.Tasks;

import org.bukkit.scheduler.BukkitRunnable;
import taurasi.marc.allimorequest.Allimorequest;

public class AutoSaveDataTask extends BukkitRunnable {
    @Override
    public void run() {
        Allimorequest.PLAYER_DATA.WriteData();
    }
}
