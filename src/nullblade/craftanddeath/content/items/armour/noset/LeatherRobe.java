package nullblade.craftanddeath.content.items.armour.noset;

import nullblade.craftanddeath.items.ArmourItem;
import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.CustomRecipe;
import nullblade.craftanddeath.main.AdvancedPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LeatherRobe extends CustomItem implements ArmourItem {

    public LeatherRobe () {
        item = new ItemStack(Material.LEATHER_CHESTPLATE);
        id = "leather_robe";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fLeather robe");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        CustomRecipe r = new CustomRecipe(item);
        r.shape(new String[]{"lll", "lll", "l l"});
        r.setCustomIngredient('l', "fire_proof_leather");
        r.category = "no set";


        CraftingManager.getInstance().registerRecipe(r);

    }

    @Override
    public int getDefence() {
        return 4;
    }

    @Override
    public void effectLoop(AdvancedPlayer p) {
        if (p.temperature < 37.6) {
            p.temperature += 0.05;
            if (p.temperature < 35.6) {
                p.temperature += 0.4;
            }
        }
    }
}
