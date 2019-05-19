package taurasi.marc.allimorequest.Observers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class EventListener implements Listener {
    private ArrayList<EntityDeathObserver> entityDeathObservers;
    private ArrayList<InventoryClickObserver> inventoryClickObservers;
    private ArrayList<CraftItemObserver> craftItemObservers;
    private ArrayList<FurnaceExtractObserver> furnaceExtractObservers;

    public EventListener(){
        entityDeathObservers  = new ArrayList<>();
        inventoryClickObservers = new ArrayList<>();
        craftItemObservers = new ArrayList<>();
        furnaceExtractObservers = new ArrayList<>();
    }

    @EventHandler
    private void OnEntityKilled(EntityDeathEvent event){
        for (EntityDeathObserver observer : entityDeathObservers){
            if(observer == null) continue;
            observer.Notify(event);
        }
    }

    @EventHandler
    private void OnInventoryClickEvent(InventoryClickEvent event){
        for (InventoryClickObserver observer : inventoryClickObservers){
            if(observer == null) continue;
            observer.Notify(event);
        }
    }

    @EventHandler
    private void OnCraftItemEvent(CraftItemEvent event){
        for (CraftItemObserver observer : craftItemObservers){
            if(observer == null) continue;
            observer.Notify(event);
        }
    }
    @EventHandler
    private void OnFurnaceExtractEvent(FurnaceExtractEvent event){
        for (FurnaceExtractObserver observer : furnaceExtractObservers){
            if(observer == null) continue;
            observer.Notify(event);
        }
    }

    public void Subscribe(EntityDeathObserver observer){
        entityDeathObservers.add(observer);
    }
    public void Unsubscribe(EntityDeathObserver observer){
        entityDeathObservers.remove(observer);
    }

    public void Subscribe(InventoryClickObserver observer){
        inventoryClickObservers.add(observer);
    }
    public void Unsubscribe(InventoryClickObserver observer){
        inventoryClickObservers.remove(observer);
    }

    public void Subscribe(CraftItemObserver observer){
        craftItemObservers.add(observer);
    }
    public void Unsubscribe(CraftItemObserver observer){
        craftItemObservers.remove(observer);
    }

    public void Subscribe(FurnaceExtractObserver observer){
        furnaceExtractObservers.add(observer);
    }
    public void Unsubscribe(FurnaceExtractObserver observer){
        furnaceExtractObservers.remove(observer);
    }
}
