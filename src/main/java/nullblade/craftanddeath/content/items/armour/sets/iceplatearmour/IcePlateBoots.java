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
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class IcePlateBoots extends CustomItem implements ArmourItem {

    public IcePlateBoots() {
        item = new ItemStack(Material.LEATHER_BOOTS);
        id = "ice_plate_boots";
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(Color.fromRGB(120, 170, 255));
        meta.setDisplayName("§fice plate boots");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        CustomRecipe r = new CustomRecipe(item);
        r.shape(new String[]{"   ", "p p", "p p"});
        r.setCustomIngredient('p', "ice_plate");
        r.category = "ice plate set";


        CraftingManager.getInstance().registerRecipe(r);

    }

    @Override
    public int getDefence() {
        return 3;
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
