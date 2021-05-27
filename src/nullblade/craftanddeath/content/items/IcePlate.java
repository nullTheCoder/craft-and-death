package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class IcePlate extends CustomItem {

    public IcePlate() {
        item = new ItemStack(Material.PAPER);
        id = "ice_plate";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fice plate");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, false);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        CustomRecipe r = new CustomRecipe(item);
        r.shape(new String[]{"rlr", "rlr", "rlr"});
        r.setVanillaIngredient('l', Material.IRON_INGOT);
        r.setCustomIngredient('r', "ice_chunk");
        r.category = "material";
        CraftingManager.getInstance().registerRecipe(r);
    }
}
