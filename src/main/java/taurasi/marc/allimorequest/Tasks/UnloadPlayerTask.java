package taurasi.marc.allimorequest.Tasks;

import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import taurasi.marc.allimorequest.Allimorequest;

public class UnloadPlayerTask extends BukkitRunnable {
    private final OfflinePlayer player;

    public UnloadPlayerTask(OfflinePlayer player) {
        this.player = player;
    }

    @Override
    public void run() {
        Allimorequest.PLAYER_DATA.UnloadPlayer(player);
    }
}
