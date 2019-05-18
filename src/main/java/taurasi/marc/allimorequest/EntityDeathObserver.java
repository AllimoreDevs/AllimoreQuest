package taurasi.marc.allimorequest;

import org.bukkit.event.entity.EntityDeathEvent;

public interface EntityDeathObserver {
    void Notify(EntityDeathEvent event);
}
