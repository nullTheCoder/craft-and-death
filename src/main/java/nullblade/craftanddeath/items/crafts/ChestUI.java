package nullblade.craftanddeath.items.crafts;

import nullblade.craftanddeath.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChestUI implements Listener {

    private final String title;
    private final Inventory inventory;
    private final Map<Integer, SlotInteractType> slots;

    public ChestUI(String title, int rowCount) {
        this.title = title;

        inventory = Bukkit.createInventory(null, rowCount * 9, title);
        slots = new HashMap<>();

        for (int i = 0; i < inventory.getSize(); i++)
            setSlotInteractType(i, SlotInteractType.HANDS_OFF);

        Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
    }


    public void setSlotInteractType(int id, SlotInteractType type) {
        slots.put(id, type);
    }

    public void setSlotInteractType(int x, int y, SlotInteractType type) {
        setSlotInteractType(y * 9 + x, type);
    }


    public void putItem(int id, ItemStack item) {
        inventory.setItem(id, item);
    }

    public void putItem(int x, int y, ItemStack item) {
        putItem(y * 9 + x, item);
    }

    public void fill(ItemStack item) {
        for (int i = 0; i < inventory.getSize(); i++)
            putItem(i, item);
    }

    public void finalizeUI(ItemStack fi) {
        ItemMeta m = fi.getItemMeta();
        StringBuilder lore = new StringBuilder();
        for (char x : title.toCharArray()) {
            lore.append("§").append(x);
        }
        List<String> lr = new ArrayList<>();
        lr.add(lore.toString());
        m.setDisplayName("");
        m.setLore(lr);
        fi.setItemMeta(m);

        inventory.setItem(0, fi);
    }

    @Deprecated
    public void show(Player player) {
        player.openInventory(inventory);
    }

    public void destroy() {
        HandlerList.unregisterAll(this);
    }

    public ItemStack getItemInSlot(int x, int y) {
        ItemStack item = inventory.getItem(y * 9 + x);
        return item == null ? new ItemStack(Material.AIR) : item;
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getClickedInventory().getItem(0) == null || !e.getClickedInventory().getItem(0).hasItemMeta() || !e.getClickedInventory().getItem(0).getItemMeta().hasLore() || !title.replace("§", "").equals(e.getClickedInventory().getItem(0).getItemMeta().getLore().get(0).replace("§", ""))) {
            return;
        }

        if (e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY)
            e.setCancelled(true);

        if (e.getRawSlot() < e.getInventory().getSize()) {
            SlotInteractType t = slots.get(e.getSlot());

            if (e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PLACE_SOME) {
                if (t == SlotInteractType.HANDS_OFF || t == SlotInteractType.TAKE_ONLY)
                    e.setCancelled(true);
            } else if ((e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_ONE || e.getAction() == InventoryAction.PICKUP_SOME || e.getAction() == InventoryAction.PICKUP_HALF) || (e.getAction() == InventoryAction.DROP_ALL_SLOT || e.getAction() == InventoryAction.DROP_ONE_SLOT) || (e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) || e.getAction() == InventoryAction.SWAP_WITH_CURSOR) {
                if (t == SlotInteractType.HANDS_OFF || t == SlotInteractType.PUT_ONLY)
                    e.setCancelled(true);
            }
        } else {
            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR)
                e.setCancelled(true);
        }

        if (e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF) {
            slotClicked(e.getCurrentItem(), e.getSlot(), (Player)e.getWhoClicked(), e.getClickedInventory());
        }
    }

    public void slotClicked(ItemStack item, int slot, Player player, Inventory inv) {};

    public Inventory getInventory() {
        return inventory;
    }
}
