package nullblade.craftanddeath.items;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CustomItem {
    public String id;
    public ItemStack item;

    public void finalizeTheItemStack() {
        ItemMeta meta = item.getItemMeta();

        List<String> lore;
        if (meta.hasLore()) {
            lore = meta.getLore();
        } else {
            lore = new ArrayList<>();
        }

        lore.add("§3" + id);

        meta.setLore(lore);
        item.setItemMeta(meta);

        ItemManager.getInstance().registerItem(this);
    }
}
