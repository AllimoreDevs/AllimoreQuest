package taurasi.marc.allimorequest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;

public class EventListener implements Listener {
    private ArrayList<EntityDeathObserver> entityDeathObservers;
    private ArrayList<InventoryClickObserver> inventoryClickObservers;

    public EventListener(){
        entityDeathObservers  = new ArrayList<>();
        inventoryClickObservers = new ArrayList<>();
    }

    @EventHandler
    private void OnEntityKilled(EntityDeathEvent event){
        for(int i = 0; i < entityDeathObservers.size(); i++){
            entityDeathObservers.get(i).Notify(event);
        }
    }

    @EventHandler
    private void OnInventoryClickEvent(InventoryClickEvent event){
        for(int i = 0; i < inventoryClickObservers.size(); i++){
            inventoryClickObservers.get(i).Notify(event);
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
}
