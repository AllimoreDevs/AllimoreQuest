package taurasi.marc.allimorequest.Observers;

import org.bukkit.event.entity.EntityDeathEvent;

public interface EntityDeathObserver {
    void Notify(EntityDeathEvent event);
}
