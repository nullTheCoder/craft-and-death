package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.CustomRecipe;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FireProofLeather extends CustomItem {

    public FireProofLeather() {
        item = new ItemStack(Material.LEATHER);
        id = "fire_proof_leather";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§ffire proof leather");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 1, false);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        CustomRecipe r = new CustomRecipe(item);
        r.shape(new String[]{" l ", "rlr", " l "});
        r.setVanillaIngredient('l', Material.LEATHER);
        r.setCustomIngredient('r', "fire_proof_fiber");
        r.category = "material";
        CraftingManager.getInstance().registerRecipe(r);
    }
}
