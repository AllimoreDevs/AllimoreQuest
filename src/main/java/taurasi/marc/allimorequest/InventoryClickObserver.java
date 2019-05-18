package taurasi.marc.allimorequest;

import org.bukkit.event.inventory.InventoryClickEvent;

public interface InventoryClickObserver {
    void Notify(InventoryClickEvent event);
}
