package nullblade.craftanddeath.content;

import nullblade.craftanddeath.items.crafts.RecipeBook;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class CraftSections {
    public static void initialize() {
        RecipeBook.getInstance().addPage("main", "f", "armour", new ItemStack(Material.DIAMOND_LEGGINGS));
        RecipeBook.getInstance().addPage("armour", "f", "no set", new ItemStack(Material.EMERALD));
        RecipeBook.getInstance().addPage("armour", "f", "sets", new ItemStack(Material.DIAMOND_BOOTS));
        ItemStack item = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromRGB(100, 150, 255));
        item.setItemMeta(meta);
        RecipeBook.getInstance().addPage("sets", "f", "ice plate set", item);
        RecipeBook.getInstance().addPage("main", "f", "material", new ItemStack(Material.SEEDS));
        RecipeBook.getInstance().addPage("main", "f", "item", new ItemStack(Material.DIAMOND));
    }
}
