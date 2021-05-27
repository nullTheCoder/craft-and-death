package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.UsableItem;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.CustomRecipe;
import nullblade.craftanddeath.main.Main;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;

public class Thermometer extends CustomItem implements UsableItem {

    public Thermometer () {
        item = new ItemStack(Material.EMERALD);
        id = "thermometer";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fThermometer");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        CustomRecipe r = new CustomRecipe(item);
        r.shape(new String[]{" a ", "gig", " g "});
        r.setVanillaIngredient('g', Material.GLASS);
        r.setVanillaIngredient('a', Material.IRON_INGOT);
        r.setCustomIngredient('i', "ice_plate");
        r.category = "item";
        CraftingManager.getInstance().registerRecipe(r);
    }

    @Override
    public void onUse(PlayerInteractEvent e) {
        e.getPlayer().sendMessage("§fCurrent temperature: §3" + new DecimalFormat("#.0").format(Main.getInstance().getPlayer(e.getPlayer()).envTemperature) + "§f˚C");
    }
}
