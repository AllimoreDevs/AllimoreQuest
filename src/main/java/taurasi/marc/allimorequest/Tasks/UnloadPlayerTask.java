package taurasi.marc.allimorequest.Tasks;

import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;
import taurasi.marc.allimorequest.Allimorequest;
import taurasi.marc.allimorequest.PlayerDataIndex;

public class UnloadPlayerTask extends BukkitRunnable {
    private final OfflinePlayer player;
    private final PlayerDataIndex playerDataIndex;

    public UnloadPlayerTask(OfflinePlayer player, PlayerDataIndex playerDataIndex) {
        this.player = player;
        this.playerDataIndex = playerDataIndex;
    }

    @Override
    public void run() {
        Allimorequest.PLAYER_DATA.UnloadPlayer(player);
    }
}
