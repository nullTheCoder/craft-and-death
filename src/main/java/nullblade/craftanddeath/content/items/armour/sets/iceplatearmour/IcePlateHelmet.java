package nullblade.craftanddeath.content.items.armour.sets.iceplatearmour;

import nullblade.craftanddeath.items.ArmourItem;
import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.CustomRecipe;
import nullblade.craftanddeath.main.AdvancedPlayer;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class IcePlateHelmet extends CustomItem implements ArmourItem {

    public IcePlateHelmet() {
        item = new ItemStack(Material.LEATHER_HELMET);
        id = "ice_plate_helmet";
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromRGB(90, 140, 255));
        meta.setDisplayName("§fice plate helmet");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        CustomRecipe r = new CustomRecipe(item);
        r.shape(new String[]{"ppp", "p p", "   "});
        r.setCustomIngredient('p', "ice_plate");
        r.category = "ice plate set";


        CraftingManager.getInstance().registerRecipe(r);

    }

    @Override
    public int getDefence() {
        return 2;
    }

    @Override
    public void effectLoop(AdvancedPlayer p) {
        if (p.temperature > 36) {
            p.temperature -= 0.01;
            if (p.temperature > 37) {
                p.temperature -= 0.01;
            }
        }
    }
}
