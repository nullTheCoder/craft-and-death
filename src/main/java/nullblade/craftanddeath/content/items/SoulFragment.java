package nullblade.craftanddeath.content.items;

import nullblade.craftanddeath.items.ConsumableItem;
import nullblade.craftanddeath.items.CustomItem;
import nullblade.craftanddeath.items.crafts.CraftingManager;
import nullblade.craftanddeath.items.crafts.CustomRecipe;
import nullblade.craftanddeath.main.AdvancedPlayer;
import nullblade.craftanddeath.main.Main;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoulFragment extends CustomItem implements ConsumableItem {

    public SoulFragment() {
        item = new ItemStack(Material.CLAY_BALL);
        id = "soul_fragment";
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§fSoul Fragment");
        meta.spigot().setUnbreakable(true);
        meta.addEnchant(Enchantment.PROTECTION_FIRE, 0, false);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);

        finalizeTheItemStack();
    }

    @Override
    public void onConsume(PlayerItemConsumeEvent e) {
        AdvancedPlayer p = Main.getInstance().getPlayer(e.getPlayer());
        p.temperature -= 2.5d;
    }
}
