package nullblade.craftanddeath.items;

import nullblade.craftanddeath.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.HashMap;
import java.util.Map;

public class ItemEventManager implements Listener {
    private static ItemEventManager instance;

    public static ItemEventManager getInstance() {
        return instance;
    }

    private final Map<String, UsableItem> useMap;
    private final Map<String, ConsumableItem> consumeMap;

    public ItemEventManager () {
        instance = this;
        useMap = new HashMap<>();
        consumeMap = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }

    public void addUsable(String id, UsableItem u) {
        useMap.put(id, u);
    }
    public void addConsumable(String id, ConsumableItem u) {
        consumeMap.put(id, u);
    }

    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        String id = ItemManager.get(e.getItem());
        if (id == null) return;
        UsableItem item = useMap.get(id);
        if (item != null) {
            item.onUse(e);
        }
    }

    @EventHandler
    public void onConsume(PlayerItemConsumeEvent e) {
        String id = ItemManager.get(e.getItem());
        if (id == null) return;
        ConsumableItem item = consumeMap.get(id);
        if (item != null) {
            item.onConsume(e);
        }
    }

}
