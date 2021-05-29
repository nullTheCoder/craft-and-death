package nullblade.craftanddeath.items;

import nullblade.craftanddeath.items.alloys.AlloyManager;
import nullblade.craftanddeath.items.alloys.OreAlloy;
import nullblade.craftanddeath.main.DamageManager;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {
    private static ItemManager instance;

    public static ItemManager getInstance() {
        return instance;
    }

    private Map<String, CustomItem> items;

    public ItemManager() {
        instance = this;
        items = new HashMap<>();
    }

    public void registerItem(CustomItem item) {
        items.put(item.id, item);


        // The great wall of ifs. TODO: get rid of it
        if (item instanceof UsableItem) {
            ItemEventManager.getInstance().addUsable(item.id, (UsableItem) item);
        }
        if (item instanceof ArmourItem) {
            DamageManager.getInstance().registerArmourPiece(item.id, (ArmourItem) item);
        }
        if (item instanceof OreAlloy) {
            AlloyManager.getInstance().registerAlloy(((OreAlloy) item).getBase(), (OreAlloy) item);
        }
        if (item instanceof ConsumableItem) {
            ItemEventManager.getInstance().addConsumable(item.id, (ConsumableItem) item);
        }
    }

    public Map<String, CustomItem> getItems() {
        return items;
    }

    public ItemStack get(String id) {
        CustomItem item = items.get(id);
        if (item == null ){
            return null;
        } else {
            return item.item;
        }
    }

    public static String get(ItemStack i) {
        if (i == null || !i.hasItemMeta() || !i.getItemMeta().hasLore() || i.getItemMeta().getLore().size() < 1) {
            return null;
        }

        return i.getItemMeta().getLore().get(i.getItemMeta().getLore().size()-1).substring(2);

    }
}
