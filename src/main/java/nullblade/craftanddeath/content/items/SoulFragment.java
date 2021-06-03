package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.CustomItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoulFragment extends CustomItem {

    public SoulFragment() {
        item = new ItemStack(Material.CLAY_BALL);
        id = "soul_fragment";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fSoul Fragment");
        meta.spigot().setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 0, false);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        finalizeTheItemStack();
    }
}
