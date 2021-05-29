package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.alloys.OreAlloy;
import nullblade.craftanddeath.items.crafts.RecipeBook;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class FireProofFiber extends CustomItem implements OreAlloy {

    public FireProofFiber() {
        item = new ItemStack(Material.STRING);
        id = "fire_proof_fiber";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§ffire proof fiber");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        RecipeBook.getInstance().addAlloy("material", new MaterialData(Material.LONG_GRASS, (byte)1), item, 5);
    }

    @Override
    public int getChance() {
        return 5;
    }

    @Override
    public boolean dropOnBreak() {
        return true;
    }

    @Override
    public boolean dropOnSmelt() {
        return false;
    }

    @Override
    public Material getBase() {
        return Material.LONG_GRASS;
    }
}
