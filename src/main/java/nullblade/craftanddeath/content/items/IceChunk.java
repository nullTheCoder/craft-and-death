package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.alloys.OreAlloy;
import nullblade.craftanddeath.items.crafts.RecipeBook;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class IceChunk extends CustomItem implements OreAlloy {

    public IceChunk() {
        item = new ItemStack(Material.INK_SACK);
        item.setDurability((short)6);
        id = "ice_chunk";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fIce chunk");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        RecipeBook.getInstance().addAlloy("material", new MaterialData(Material.ICE), item, 20);
    }

    @Override
    public int getChance() {
        return 20;
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
        return Material.ICE;
    }
}
