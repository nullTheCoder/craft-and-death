package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.ConsumableItem;
import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.UsableItem;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.CustomRecipe;
import nullblade.craftanddeath.main.AdvancedPlayer;
import nullblade.craftanddeath.main.Main;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;

public class IceBottle extends CustomItem implements ConsumableItem {

    public IceBottle() {
        item = new ItemStack(Material.POTION);
        id = "ice_bottle";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fIce bottle");
        meta.spigot().setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        item.setItemMeta(meta);

        finalizeTheItemStack();

        CustomRecipe r = new CustomRecipe(item);
        r.shape(new String[]{"   ", " c ", " b "});
        r.setVanillaIngredient('b', Material.GLASS_BOTTLE);
        r.setCustomIngredient('c', "ice_chunk");
        r.category = "item";
        CraftingManager.getInstance().registerRecipe(r);
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent e) {
        AdvancedPlayer p = Main.getInstance().getPlayer(e.getPlayer());
        p.temperature -= 2.5d;
    }
}
